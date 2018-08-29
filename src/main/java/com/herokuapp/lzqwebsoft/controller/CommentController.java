package com.herokuapp.lzqwebsoft.controller;

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

import com.herokuapp.lzqwebsoft.pojo.Article;
import com.herokuapp.lzqwebsoft.pojo.Comment;
import com.herokuapp.lzqwebsoft.pojo.User;
import com.herokuapp.lzqwebsoft.service.ArticleService;
import com.herokuapp.lzqwebsoft.service.CommentService;
import com.herokuapp.lzqwebsoft.util.CommonConstant;
import com.herokuapp.lzqwebsoft.util.StringUtil;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommentController extends BaseController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private MessageSource messageSource;

    @ResponseBody
    @RequestMapping("/comment/add.html")
    public String add(Comment comment, String parent_comment_id, String root_comment_id, String validateCode, ModelMap model, HttpServletRequest request, HttpSession session,
                      HttpServletResponse response) {
        // 验证数据的合法性
        Locale locale = request.getLocale();
        List<String> errors = new ArrayList<String>();

        // 判断用户是否登录,则博主不用输入昵称与网址
        User user = (User) session.getAttribute(CommonConstant.LOGIN_USER);
        if (user != null) {
            comment.setReviewer(user.getUserName());
            comment.setWebsite("http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath());
            // 标记此条评论由博主产生
            comment.setIsBlogger(true);
        } else {
            // 只有未登录的用户才需要验证图片验证码
            String captcha = (String) session.getAttribute(CommonConstant.CAPTCHA);
            if (!validateCode.equalsIgnoreCase(captcha)) {
                errors.add(messageSource.getMessage("info.invalid.captcha", null, locale));
            }
            // 验证通过后清除SESSION验证码
            session.removeAttribute(CommonConstant.CAPTCHA);

            // 记录评论者的IP，及归属地
            String ip = getIpAddr(request);
            String local = getIPLocation(ip);
            comment.setFromIP(ip);
            comment.setFromLocal(local);
        }
        String reviewer = comment.getReviewer();
        String contentStr = comment.getContent();
        String website = comment.getWebsite();
        if (reviewer == null || reviewer.trim().length() <= 0) {
            String reviewerLabel = messageSource.getMessage("page.label.reviewer", null, locale);
            errors.add(messageSource.getMessage("info.required", new Object[]{reviewerLabel}, locale));
        } else if (reviewer.trim().length() > 80) {
            String reviewerLabel = messageSource.getMessage("page.label.reviewer", null, locale);
            errors.add(messageSource.getMessage("info.length.long", new Object[]{reviewerLabel, 80}, locale));
        }
        if (website != null && website.trim().length() > 0 && !website.matches("^((http[s]?)(://))(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*$")) {
            errors.add(messageSource.getMessage("info.invalid.website", null, locale));
        }
        if (contentStr == null || contentStr.trim().length() <= 0) {
            String contentLabel = messageSource.getMessage("page.label.content", null, locale);
            errors.add(messageSource.getMessage("info.required", new Object[]{contentLabel}, locale));
        } else {
            contentStr = contentStr.toLowerCase();
            contentStr = contentStr.replaceAll("<(img|embed).*?>", "K").replaceAll("<.*?>", "").replaceAll("\r\n|\n|\r/g", "");
            contentStr = contentStr.trim();
            if (contentStr.length() > 120) {
                String contentLabel = messageSource.getMessage("page.label.content", null, locale);
                errors.add(messageSource.getMessage("info.length.long", new Object[]{contentLabel, 120}, locale));
            }
        }
        // 验证当前博客是否允许评论
        Article article = comment.getArticle();
        article = articleService.get(article.getId());
        if (article == null || !article.getAllowComment()) {
            errors.add(messageSource.getMessage("info.article.notallowcomment", new Object[]{}, locale));
        }

        // 校验用户数据错误，直接返回JSON信息
        if (errors.size() > 0) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json; charset=UTF-8");
            return errorJSON(errors);
        }
        // 评论内容去XSS
        String content = comment.getContent();
        comment.setContent(StringUtil.xssCharClear(content, request));

        // 判断是否是子评论
        if (parent_comment_id != null && parent_comment_id.trim().length() > 0) {
            long parentId = Long.parseLong(parent_comment_id);
            Comment parnetComment = new Comment();
            parnetComment.setId(parentId);
            comment.setParentComment(parnetComment);
            // 判断是否存在根评论
            if (root_comment_id != null && root_comment_id.trim().length() > 0) {
                long rootId = Long.parseLong(root_comment_id);
                Comment rootComment = new Comment();
                rootComment.setId(rootId);
                comment.setRootComment(rootComment);
            } else {
                comment.setRootComment(parnetComment);
            }
        }

        commentService.save(comment);

        String articleId = article.getId();
        List<Comment> comments = commentService.getAllParentComment(articleId);
        model.addAttribute("comments", comments);

        // 用于标记用户评论是否成功
        request.setAttribute("comment_status", true);
        return render("_article_comments", model, request, response);
    }

    @RequestMapping("/comment/delete/{commentId}.html")
    public String delete(@PathVariable("commentId") long commentId, ModelMap model) {
        String articleId = commentService.delete(commentId);

        List<Comment> comments = commentService.getAllParentComment(articleId);
        model.addAttribute("comments", comments);

        Comment comment = new Comment();
        model.addAttribute("comment", comment);
        return "_article_comments";
    }
}
