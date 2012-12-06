package com.herokuapp.lzqwebsoft.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.herokuapp.lzqwebsoft.dao.UserDAO;
import com.herokuapp.lzqwebsoft.pojo.User;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDAO userDAO;

	@Override
	public User loginService(String username, String password) {
		User user = userDAO.getUser(username, password);
		if (user!=null) {
			user.setLastLogin(new Date());
			userDAO.update(user);
			return user;
		} else {
			return null;
		}
	}
}
