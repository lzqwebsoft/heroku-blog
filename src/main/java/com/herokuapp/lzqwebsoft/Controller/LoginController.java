package com.herokuapp.lzqwebsoft.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.herokuapp.lzqwebsoft.pojo.User;

@Controller
@SessionAttributes("user")
public class LoginController {
    
    @ModelAttribute("user")
    public User getUser() {
        User user = new User();
        return user;
    }
    
    @RequestMapping(value="/login")
    public void login(ModelMap modelMap, HttpServletRequest requset,
            HttpServletResponse response) {
        //User user = (User)modelMap.get("user");
        // 用户验证
        // ...... user.setUserName();
        
    }
    
    @RequestMapping(value="/logout")
    public String logout() {
        return "index";
    }
}
