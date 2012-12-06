package com.herokuapp.lzqwebsoft.Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.herokuapp.lzqwebsoft.pojo.User;
import com.herokuapp.lzqwebsoft.service.UserService;

@Controller
@SessionAttributes("user")
public class LoginController {
	
	@Autowired
	private UserService userService;
    
    @ModelAttribute("user")
    public User getUser() {
        User user = new User();
        return user;
    }
    
    @RequestMapping(value="/login")
    public void login(String username, String password, String currentPath,
    		HttpServletResponse response) {
        
        // 用户验证
        User user = userService.loginService(username, password);
        if(user!=null) {
        	try {
				response.sendRedirect(currentPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
        } else {
        	try {
				PrintWriter out = response.getWriter();
				response.setStatus(HttpServletResponse.SC_OK);
				response.setContentType("text/pain");
				out.print("登录失败！");
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
    
    @RequestMapping(value="/logout")
    public String logout() {
        return "index";
    }
}
