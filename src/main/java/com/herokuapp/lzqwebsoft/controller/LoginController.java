package com.herokuapp.lzqwebsoft.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.herokuapp.lzqwebsoft.pojo.User;
import com.herokuapp.lzqwebsoft.service.UserService;
import com.herokuapp.lzqwebsoft.util.CommonConstant;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageSource messageSource;
    
    @RequestMapping(value="/login")
    public void login(String username, String password, HttpServletResponse response,
    		HttpSession session, Locale locale) {
        // 用户验证
        PrintWriter out = null;
        try {
			out = response.getWriter();
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("aplicaction/json;charset=UTF-8");
			StringBuffer json = new StringBuffer();
			if(username!=null&&username.trim().length()>0
					&&password!=null&&password.trim().length()>0) {
				User user = userService.loginService(username, password);
				if(user!=null) {
					json.append("{\"status\": \"SUCCESS\", \"messages\": \"\"}");
					session.setAttribute(CommonConstant.LOGIN_USER, user);
		        } else {
		        	json.append("{\"status\": \"FAILURE\", \"messages\": \"")
		        	    .append(messageSource.getMessage("info.login.error", null, locale)).append("\"}");
		        }
			} else {
				json.append("{\"status\": \"FAILURE\", \"messages\": \"")
				    .append(messageSource.getMessage("info.login.nameOrPasswordEmpty", null, locale)).append("\"}");
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
    public String logout(HttpServletRequest request, HttpSession session) {
    	// 销毁session对象
    	if(session.getAttribute(CommonConstant.LOGIN_USER)!=null)
    	    session.invalidate();
    	// 从请求的Header的REFERER属性中得到上一次请求的URL
    	String lastReqUrl = request.getHeader("Referer");
    	if(lastReqUrl!=null&&lastReqUrl.trim().length()>0) {
    	    return "redirect:"+lastReqUrl;
    	} else {
    	    // 如果没有则重定向到index画面
    	    return "redirect:index.html";
    	}
    }
}
