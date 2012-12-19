package com.herokuapp.lzqwebsoft.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.herokuapp.lzqwebsoft.pojo.BlogInfo;
import com.herokuapp.lzqwebsoft.service.BlogInfoService;
import com.herokuapp.lzqwebsoft.util.CommonConstant;

/**
 * 用于显示用户配置的博客信息
 * @author zqluo
 *
 */
public class BlogInfoInterceptor implements HandlerInterceptor{
	@Autowired
	private BlogInfoService blogInfoService;

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception exception)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest requset, HttpServletResponse response,
			Object handler, ModelAndView model) throws Exception {
		BlogInfo blogInfo = blogInfoService.getSystemBlogInfo();
		requset.setAttribute(CommonConstant.BLOG_INFO, blogInfo);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		return true;
	}

}
