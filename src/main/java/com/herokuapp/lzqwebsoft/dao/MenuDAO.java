package com.herokuapp.lzqwebsoft.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.herokuapp.lzqwebsoft.pojo.Menu;

@Repository("menuDAO")
public class MenuDAO extends BaseDAO {

    /**
     * 返回一般性的菜单列表
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Menu> getGeneralMenu() {
        List<Menu> list = (List<Menu>) getSession().createQuery("from Menu u where u.isLogin=?0").setParameter(0, false).getResultList();
        return list;
    }

    /**
     * 返回全部的菜单列表
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Menu> getALLMenu() {
        List<Menu> list = (List<Menu>) getSession().createQuery("from Menu").list();
        return list;
    }
}
