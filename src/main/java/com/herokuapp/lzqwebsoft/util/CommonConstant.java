package com.herokuapp.lzqwebsoft.util;

/**
 * 定义的一些公共的常量
 * @author zqluo
 *
 */
public class CommonConstant {
	/**
	 * 记录在Session对象中的登录的用户属性名
	 */
	public static final String LOGIN_USER="user";
	/**
	 * 记录在Request对象中的菜单属性名
	 */
	public static final String MENUS="menus";
	/**
	 * 记录在Requset对象中的博客信息属性名
	 */
	public static final String BLOG_INFO="blogInfo";
	/**
	 * 记录在Session对象中的Message属性名，方便跨action之间的消息转递
	 */
	public static final String MESSAGES="messages";
	/**
	 * 记录用户没有权限，被拦截的URL
	 */
	public static final String LAST_REQUEST_URL="lastRequestURL";
	/**
	 * 保存文章文本文件的目录
	 */
	public static final String ARTICLES_DIR=System.getProperty("web.contextPath")+"/WEB-INF/articles";
	
	/**
	 * 上传的图片的保存目录名
	 */
	public static final String IMAGE_DIR=System.getProperty("web.contextPath")+"/upload-images";
	
	/**
	 * 验证码
	 */
	public static final String CAPTCHA = "captcha";
}
