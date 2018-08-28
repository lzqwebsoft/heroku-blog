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
    User loginService(String username, String password);
	
	/**
	 * 更新用户密码
	 * @param user 需要更改密码的用户对象
	 * @param newPassword 新密码
	 * @return 更新密码后的User对象
	 */
    User changePassword(User user, String newPassword);
	
	/**
	 * 得到博主
	 * @return User对象
	 */
    User getBlogOwner();
	
	/**
	 * 查询所有用户数据，查看是否存在指定的邮箱。
	 * @param email 待查询的邮箱
	 * @return 如果存在指定的邮箱，则返回对应的用户，没有则返回为null
	 */
    User validEmail(String email);
	
	/**
	 * 更新User对象数据
	 * @param user User对象
	 */
    void update(User user);
	
	/**
	 * 由用户ID得到用户对象
	 * @param userid 用户id
	 * @return 用户对象
	 */
    User getUser(Long userid);
}
