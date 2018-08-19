package com.herokuapp.lzqwebsoft.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.herokuapp.lzqwebsoft.dao.MenuDAO;
import com.herokuapp.lzqwebsoft.pojo.Menu;

@Service("menuService")
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDAO menuDAO;

    @Override
    public List<Menu> getAllMenus(boolean isLogined) {
        if (isLogined) {
            return menuDAO.getALLMenu();
        } else {
            return menuDAO.getGeneralMenu();
        }
    }

}
