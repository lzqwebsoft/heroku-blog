package com.herokuapp.lzqwebsoft.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class BaseDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	private HibernateTemplate ht = null;
	
    public HibernateTemplate getHibernateTemple() {
        if(ht==null) {
            ht = new HibernateTemplate(sessionFactory);
        }
        return ht;
    }
}
