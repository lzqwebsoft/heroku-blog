package com.herokuapp.lzqwebsoft.dao;

import com.herokuapp.lzqwebsoft.pojo.User;
import java.util.List;

import javax.persistence.NoResultException;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

@Repository("userDAO")
public class UserDAO extends BaseDAO {

    public void save(User user) {
        getSession().save(user);
    }

    public void update(User user) {
        getSession().update(user);
    }

    public User getUser(Long userid) {
        User user = (User) getSession().get(User.class, userid);
        return user;
    }

    public void delete(User user) {
        getSession().delete(user);
    }

    public User getUserByName(String username) {
        try {
            User user = (User) getSession().createQuery("from User u where u.userName=?0").setParameter(0, username).getSingleResult();
            return user;
        } catch (NoResultException e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public List<User> getUsers() {
        List<User> users = new ArrayList<User>();
        users = (List<User>) getSession().createQuery("from User").list();
        return users;
    }

    public User getUserByEmail(String email) {
        try {
            User user = (User) getSession().createQuery("from User u where u.email=?0").setParameter(0, email).getSingleResult();
            return user;
        } catch (NoResultException e) {
            return null;
        }
    }
}