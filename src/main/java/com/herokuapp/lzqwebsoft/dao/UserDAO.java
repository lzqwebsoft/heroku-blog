package com.herokuapp.lzqwebsoft.dao;

import com.herokuapp.lzqwebsoft.pojo.User;
import java.util.List;
import java.util.ArrayList;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class UserDAO extends HibernateDaoSupport {

    public void save(User user) {
        getHibernateTemplate().save(user);
        
        System.out.println("A user saved!");
    }

    public void update(User user) {
    	getHibernateTemplate().update(user);

        System.out.println("A user updated!");
    }

	public User getUser(Long userid) {
		User user = (User)getHibernateTemplate().get(User.class, userid);
		return user;
	}

	public void delete(User user) {
		getHibernateTemplate().delete(user);
	}
	
	public User getUser(final String username, final String password) {
		System.out.println("query user by name and password.");
//		getHibernateTemplate()
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		users = (List<User>)getHibernateTemplate().find("from users");

        System.out.println("Get all users!");

        return users;
	}

}