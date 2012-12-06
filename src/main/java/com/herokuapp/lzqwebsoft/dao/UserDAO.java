package com.herokuapp.lzqwebsoft.dao;

import com.herokuapp.lzqwebsoft.pojo.User;
import java.util.List;
import java.util.ArrayList;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("userDAO")
public class UserDAO{
    @Autowired
    private SessionFactory sessionFactory;
    
    private HibernateTemplate ht = null;
    private HibernateTemplate getHibernateTemple() {
        if(ht==null) {
            ht = new HibernateTemplate(sessionFactory);
        }
        return ht;
    }

    public void save(User user) {
        getHibernateTemple().save(user);
        
        System.out.println("A user saved!");
    }

    public void update(User user) {
        getHibernateTemple().update(user);

        System.out.println("A user updated!");
    }

	public User getUser(Long userid) {
		User user = (User)getHibernateTemple().get(User.class, userid);
		return user;
	}

	public void delete(User user) {
	    getHibernateTemple().delete(user);
	}
	
	@SuppressWarnings("unchecked")
    public User getUser(String username, String password) {
		System.out.println("query user by name and password.");
		List<User> list = (List<User>)getHibernateTemple().find("from User u where u.userName=? and u.password=?", new Object[]{username, password});
		if(list!=null&&list.size()>0) {
		    User user = list.get(0);
		    return user;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		users = (List<User>)sessionFactory.getCurrentSession().createCriteria("from user");

        System.out.println("Get all users!");

        return users;
	}

}