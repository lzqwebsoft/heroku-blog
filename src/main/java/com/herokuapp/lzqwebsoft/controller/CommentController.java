package com.herokuapp.lzqwebsoft.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.herokuapp.lzqwebsoft.pojo.Comment;
import com.herokuapp.lzqwebsoft.pojo.User;
import com.herokuapp.lzqwebsoft.service.CommentService;
import com.herokuapp.lzqwebsoft.service.UserService;
import com.herokuapp.lzqwebsoft.util.CommonConstant;
import com.herokuapp.lzqwebsoft.util.MailUtil;

@Controller
public class CommentController {
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping("/comment/add")
	public String add(Comment comment, String parent_comment_id,
			ModelMap model, HttpServletRequest request, HttpSession session) {
		// 判断是否是子评论
		if(parent_comment_id!=null&&parent_comment_id.trim().length()>0) {
			long parentId = Long.parseLong(parent_comment_id);
			Comment parnetComment = new Comment();
			parnetComment.setId(parentId);
			comment.setParentComment(parnetComment);
		}
		
		// 判断用户是否登录,则博主不用输入昵称与网址
		User user = (User)session.getAttribute(CommonConstant.LOGIN_USER);
		if(user!=null) {
			comment.setReviewer(user.getUserName());
			comment.setWebsite(request.getRemoteHost()+":"+request.getLocalPort()+request.getContextPath());
		}
		
		// 为网址加上http
		String webSite = comment.getWebsite();
		if(webSite!=null&&webSite.trim().length()>0)
			comment.setWebsite("http://"+webSite);
		commentService.save(comment);
		
		String articleId = comment.getArticle().getId();
		List<Comment> comments = commentService.getAllParentComment(articleId);
		model.addAttribute("comments", comments);
		
		if(user==null) {
		    // 发送邮件给博言主，通知有新评论
		    user = userService.getBlogOwner();
            if(user.getEmail()!=null){
                Locale locale = request.getLocale();
                StringBuffer link = new StringBuffer("http://").append(request.getRemoteAddr());
                int port = request.getLocalPort();
                if(port!=80)
                    link.append(":").append(port);
                link.append("").append(request.getContextPath())
                    .append("/show/").append(articleId).append(".html").toString();
                final String content = messageSource.getMessage("email.addComment.content", new Object[]{user.getUserName(), 
                		comment.getArticle().getTitle(), link.toString()}, locale);
                final String title = messageSource.getMessage("email.addComment.title", null, locale);
                final String to = user.getEmail();
                new Thread() {
                    @Override
                    public void run() {
                        MailUtil.sendEMail(to, title, content);
                    }
                    
                }.start();
            }
		}
		
		return "_article_comments";
	}
	
	@RequestMapping("/comment/delete/{commentId}")
	public String delete(@PathVariable("commentId")long commentId, ModelMap model) {
		String articleId = commentService.delete(commentId);
		
		List<Comment> comments = commentService.getAllParentComment(articleId);
		model.addAttribute("comments", comments);
		
		Comment comment = new Comment();
		model.addAttribute("comment", comment);
		return "_article_comments";
	}
}
