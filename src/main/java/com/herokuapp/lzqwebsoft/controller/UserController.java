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
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.herokuapp.lzqwebsoft.pojo.ChangePasswordUserBean;
import com.herokuapp.lzqwebsoft.pojo.User;
import com.herokuapp.lzqwebsoft.service.UserService;
import com.herokuapp.lzqwebsoft.util.CommonConstant;
import com.herokuapp.lzqwebsoft.util.SHA1Util;

/**
 * 
 * 主要用于用户的登录、改密、及找回密码
 * @author zqluo
 *
 */
@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageSource messageSource;
    
	// JSON登录
    @RequestMapping(value="/login")
    public void login(String username, String password, HttpServletResponse response,
    		HttpSession session, Locale locale) {
        // 用户验证
        PrintWriter out = null;
        try {
			out = response.getWriter();
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("application/json;charset=UTF-8");
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
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			if(out!=null) {
				out.close();
			}
		}
    }
    
    // GET链接到登录页面
    @RequestMapping(value="/signIn", method=RequestMethod.GET) 
	public String signInGet(ModelMap model){
    	model.addAttribute("user", new User());
		return "login";
	}
	
    // POST处理登录
	@RequestMapping(value="/signIn", method=RequestMethod.POST) 
	public String signInPOST(@ModelAttribute("user")User user, String validateCode, HttpServletRequest request,
			HttpSession session, Errors errors){
	    // 验证用户信息
	    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "info.login.nameOrPasswordEmpty");
		if(errors.hasErrors()) {
		    return "login";
		} else {
		    User dbuser = userService.loginService(user.getUserName(), user.getPassword());
	        if(dbuser!=null) {
	            session.setAttribute(CommonConstant.LOGIN_USER, dbuser);
	            String lastReqUrl = request.getHeader("Referer");
	            if(lastReqUrl!=null&&lastReqUrl.trim().length()>0&&!lastReqUrl.endsWith("signIn.html")) {
	                return "redirect:"+lastReqUrl;
	            } else {
	                // 如果没有则重定向到index画面
	                return "redirect:index.html";
	            }
	        } else {
	            return "login";
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
    	    // 如果没有则重定向到signIn画面
    	    return "redirect:signIn.html";
    	}
    }
    
    @RequestMapping(value="/changepwd_handle", method=RequestMethod.POST)
    public String changePWD(@ModelAttribute("userBean")ChangePasswordUserBean userBean, ModelMap model,
    		HttpSession session, Errors errors, Locale locale) {
    	// 使用ValidationUtils工具包验证数据的合法性
        String passwordLabel = messageSource.getMessage("page.label.changepwd.password", null, locale);
        String newPasswordLabel = messageSource.getMessage("page.label.changepwd.newpassword", null, locale);
        String confirmPasswordLabel = messageSource.getMessage("page.label.changepwd.confirmPassword", null, locale);
    	ValidationUtils.rejectIfEmpty(errors, "password", "info.changepwd.required", new Object[]{passwordLabel});
    	ValidationUtils.rejectIfEmpty(errors, "newPassword", "info.changepwd.required", new Object[]{newPasswordLabel});
    	ValidationUtils.rejectIfEmpty(errors, "confirmPassword", "info.changepwd.required", new Object[]{confirmPasswordLabel});
    	if(!errors.hasErrors()) {
    	    if(userBean.getNewPassword().length()<6||userBean.getNewPassword().length()>20) {
                errors.rejectValue("newPassword", "info.changepwd.newpassword.invalid");
            } else if (!userBean.getConfirmPassword().equals(userBean.getNewPassword())){
                errors.rejectValue("confirmPassword", "info.changepwd.confirmPassword.invalid");
            } else {
                // 判断用户输入的旧密码是否正确
                User user = (User)session.getAttribute(CommonConstant.LOGIN_USER);
                String hashPassword = SHA1Util.saltPassword(user.getSalt(), userBean.getPassword());
                if(user.getPassword().equals(hashPassword)) {
                    // 这里UserService更新后，user对象也接着更新，因为传入的为引用
                    userService.changePassword(user, userBean.getNewPassword());
                    session.setAttribute(CommonConstant.LOGIN_USER, user);
                    session.setAttribute(CommonConstant.MESSAGES, messageSource.getMessage("info.changepwd.success", null, locale));
                    return "redirect:change_password.html";
                } else {
                    errors.rejectValue("password", "info.changepwd.password.invalid");
                }
            }
    	}
    	return "change_password";
    }
}
