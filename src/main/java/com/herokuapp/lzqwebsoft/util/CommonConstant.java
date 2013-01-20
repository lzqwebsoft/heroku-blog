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
	 * 验证码
	 */
	public static final String CAPTCHA = "captcha";
	/**
	 * 用户登录错误的次数
	 */
	public static final String ERROR_LOGIN_COUNT = "error_login_count";
	/**
	 * 系统默认的时区
	 */
	public static final String DEAFULT_TIME_ZONE="GMT+8";
}
