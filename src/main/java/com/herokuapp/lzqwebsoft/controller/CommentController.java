package com.herokuapp.lzqwebsoft.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
			ModelMap model, HttpServletRequest request,
			HttpSession session, HttpServletResponse response) {
	    // 判断用户是否登录,则博主不用输入昵称与网址
        User user = (User)session.getAttribute(CommonConstant.LOGIN_USER);
        if(user!=null) {
            comment.setReviewer(user.getUserName());
            comment.setWebsite(request.getRemoteHost()+":"+request.getLocalPort()+request.getContextPath());
        }
        
		// 验证数据的合法性
		Locale locale = request.getLocale();
		List<String> errors = new ArrayList<String>();
		String reviewer = comment.getReviewer();
		String contentStr = comment.getContent();
		if(reviewer==null||reviewer.trim().length()<=0) {
			String reviewerLabel = messageSource.getMessage("page.label.reviewer", null, locale);
			errors.add(messageSource.getMessage("info.required", new Object[]{reviewerLabel}, locale));
		}
		if(contentStr==null||contentStr.trim().length()<=0) {
			String contentLabel = messageSource.getMessage("page.label.content", null, locale);
			errors.add(messageSource.getMessage("info.required", new Object[]{contentLabel}, locale));
		}
		if(errors.size()>0) {
			PrintWriter out = null;
			try {
				out = response.getWriter();
				response.setStatus(HttpServletResponse.SC_OK);
				response.setContentType("application/json; charset=UTF-8");
				
				//生成错误的JSON信息，用于提于用户
				StringBuffer json = new StringBuffer("{\"status\": \"FAILURE\", \"messages\": [");
				for(String error : errors)
					json.append("\"").append(error).append("\",");
				json.deleteCharAt(json.length()-1);
				json.append("]}");
				
				out.print(json);
				out.close();
				return null;
			} catch(IOException e) {
				e.printStackTrace();
				if(out!=null) {
					out.close();
				}
			}
		}
		
		// 判断是否是子评论
		if(parent_comment_id!=null&&parent_comment_id.trim().length()>0) {
			long parentId = Long.parseLong(parent_comment_id);
			Comment parnetComment = new Comment();
			parnetComment.setId(parentId);
			comment.setParentComment(parnetComment);
		}
		
		// 为网址加上http
		String webSite = comment.getWebsite();
		if(webSite!=null&&webSite.trim().length()>0)
			comment.setWebsite("http://"+webSite);
		commentService.save(comment);
		
		String articleId = comment.getArticle().getId();
		List<Comment> comments = commentService.getAllParentComment(articleId);
		model.addAttribute("comments", comments);
		
		// 当用于为空，说明是外人的评论，则要求发送邮件给予通知
		if(user==null) {
		    // 发送邮件给博言主，通知有新评论
		    user = userService.getBlogOwner();
            if(user.getEmail()!=null){
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
