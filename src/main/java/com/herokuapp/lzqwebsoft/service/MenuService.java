package com.herokuapp.lzqwebsoft.service;

import java.util.List;

import com.herokuapp.lzqwebsoft.pojo.Menu;

/**
 * 管理菜单的Service
 * @author zqluo
 *
 */
public interface MenuService {
	/**
	 * 根据用户是否登录来得到数据库中指定权限的菜单项。
	 * @param isLogin 用户是否登录
	 * @return 指定的菜单列表
	 */
	public List<Menu> getAllMenus(boolean isLogined);
}
