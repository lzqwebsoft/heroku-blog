package com.herokuapp.lzqwebsoft.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.herokuapp.lzqwebsoft.dao.UserDAO;
import com.herokuapp.lzqwebsoft.pojo.User;
import com.herokuapp.lzqwebsoft.util.SHA1Util;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDAO userDAO;

	@Override
	public User loginService(String username, String password) {
		User user = userDAO.getUserByName(username);
		if(user!=null){
			String hashPassword = SHA1Util.saltPassword(user.getSalt(), password);
			if (hashPassword.equals(user.getPassword())) {
				user.setLastLogin(new Date());
				userDAO.update(user);
				return user;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	@Override
	public User changePassword(User user, String newPassword) {
		String salt = SHA1Util.generateSalt();
		String hashPassword = SHA1Util.saltPassword(salt, newPassword);
		user.setSalt(salt);
		user.setPassword(hashPassword);
		user.setUpdateAt(new Date());
		userDAO.update(user);
		return user;
	}
	
	@Override
	public User getBlogOwner(){
		List<User> users = userDAO.getUsers();
		if(users!=null&&users.size()>0)
			return users.get(0);
		return null;
	}
}
