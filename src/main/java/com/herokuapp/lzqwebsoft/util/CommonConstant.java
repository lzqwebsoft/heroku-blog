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
}
