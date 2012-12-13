package com.herokuapp.lzqwebsoft.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.herokuapp.lzqwebsoft.pojo.Menu;

@Repository("menuDAO")
public class MenuDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	private HibernateTemplate ht = null;
    private HibernateTemplate getHibernateTemple() {
        if(ht==null) {
            ht = new HibernateTemplate(sessionFactory);
        }
        return ht;
    }
	
	/**
	 * 返回一般性的菜单列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> getGeneralMenu() {
		List<Menu> list = (List<Menu>)getHibernateTemple().find("from Menu u where u.isLogin=?", new Object[]{false});
		return list;
	}
	
	/**
	 * 返回全部的菜单列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> getALLMenu() {
		List<Menu> list = (List<Menu>)getHibernateTemple().find("from Menu u");
		return list;
	}
}
