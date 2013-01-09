package com.herokuapp.lzqwebsoft.service;

import com.herokuapp.lzqwebsoft.pojo.User;

/**
 * 
 * 管理数据库中的用户信息
 * @author Johnny
 *
 */
public interface UserService {
	
	/**
	 * 根据用户名与帐号查询数据库得到数据库中的User对象，如果数据库中不存在，则返回为null。
	 * @param username 登录帐号
	 * @param password 登录密码
	 * @return 数据库中对应的User对象，不存在则返回null
	 */
	public User loginService(String username, String password);
	
	/**
	 * 更新用户密码
	 * @param user 需要更改密码的用户对象
	 * @param newPassword 新密码
	 * @return 更新密码后的User对象
	 */
	public User changePassword(User user, String newPassword);
	
	/**
	 * 得到博主
	 * @return User对象
	 */
	public User getBlogOwner();
}
