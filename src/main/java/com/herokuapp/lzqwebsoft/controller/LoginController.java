package com.herokuapp.lzqwebsoft.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.herokuapp.lzqwebsoft.pojo.User;
import com.herokuapp.lzqwebsoft.service.UserService;
import com.herokuapp.lzqwebsoft.util.CommonConstant;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
    
    @RequestMapping(value="/login")
    public void login(String username, String password, HttpServletResponse response,
    		HttpSession session) {
        // 用户验证
        PrintWriter out = null;
        try {
			out = response.getWriter();
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("text/json;charset=UTF-8");
			StringBuffer json = new StringBuffer();
			if(username!=null&&username.trim().length()>0
					&&password!=null&&password.trim().length()>0) {
				User user = userService.loginService(username, password);
				if(user!=null) {
					json.append("{\"status\": \"SUCCESS\", \"messages\": \"\"}");
					session.setAttribute(CommonConstant.LOGIN_USER, user);
		        } else {
		        	json.append("{\"status\": \"FAILURE\", \"messages\": \"")
		        	    .append("帐号或密码错误！").append("\"}");
		        }
			} else {
				json.append("{\"status\": \"FAILURE\", \"messages\": \"")
				    .append("帐号或密码不能为空！").append("\"}");
			}
			out.print(json);
		} catch (IOException e) {
			e.printStackTrace();
			if(out!=null) {
				out.close();
			}
		}
    }
    
    @RequestMapping(value="/logout")
    public String logout(HttpServletRequest requset, SessionStatus sessionStatus) {
    	// 清空Session中的对象
    	sessionStatus.setComplete();
        return "index";
    }
}
