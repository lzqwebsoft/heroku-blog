package com.herokuapp.lzqwebsoft.interceptor;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.herokuapp.lzqwebsoft.pojo.BlogInfo;
import com.herokuapp.lzqwebsoft.pojo.User;
import com.herokuapp.lzqwebsoft.service.BlogInfoService;
import com.herokuapp.lzqwebsoft.service.UserService;
import com.herokuapp.lzqwebsoft.util.CommonConstant;
import com.herokuapp.lzqwebsoft.util.MailUtil;
import com.herokuapp.lzqwebsoft.util.StringUtil;

/**
 * 当有网友评论博客时，发送邮件给博主
 * 
 * @author zqluo
 *
 */
public class SendEmailInterceptor implements HandlerInterceptor {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private BlogInfoService blogInfoService;

    @Autowired
    private UserService userService;

    // 用于指示是否评论成功
    private boolean isSuccessed;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
        User user = (User) request.getSession().getAttribute(CommonConstant.LOGIN_USER);
        // 当用于为空，并添加评论成功，说明是外人的评论，则要求发送邮件给予通知
        if (user == null && isSuccessed) {
            // 发送邮件给博言主，通知有新评论
            user = userService.getBlogOwner();
            BlogInfo blogInfo = blogInfoService.getSystemBlogInfo();
            // 使用的邮件为博客关联邮箱
            String email = blogInfo.getEmail();
            if (email != null && email.trim().length() > 0) {
                Locale locale = request.getLocale();

                StringBuffer link = new StringBuffer("http://").append(request.getServerName());

                String articleId = request.getParameter("article.id");
                String articleTitile = request.getParameter("article.title");
                String commentContent = request.getParameter("content");
                commentContent = StringUtil.xssCharClear(commentContent, request); // 内容清理XSS
                int port = request.getServerPort();
                if (port != 80 && port != 443)
                    link.append(":").append(port);
                link.append(request.getContextPath()).append("/show/").append(articleId).append(".html").toString();
                String content = messageSource.getMessage("email.addComment.content", new Object[] { user.getUserName(), articleTitile, commentContent, link.toString() }, locale);
                String title = messageSource.getMessage("email.addComment.title", null, locale);
                String to = email;
                // 发送邮件
                MailUtil.sendEMail(to, title, content);

                isSuccessed = false;
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView view) {
        if (view != null) {
            String viewName = view.getViewName();
            isSuccessed = viewName != null && viewName.equalsIgnoreCase("_article_comments");
        } else {
            isSuccessed = false;
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return true;
    }

}
