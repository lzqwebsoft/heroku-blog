package com.herokuapp.lzqwebsoft.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.herokuapp.lzqwebsoft.exception.HttpNotFoundException;
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
import com.herokuapp.lzqwebsoft.util.MailUtil;
import com.herokuapp.lzqwebsoft.util.SHA1Util;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 主要用于用户的登录、改密、及找回密码
 *
 * @author zqluo
 */
@Controller
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    // JSON登录(从2.0开始的UI不再使用该方法)
    @ResponseBody
    @RequestMapping(value = "/login.html")
    public String login(String username, String password, String captcha, HttpServletResponse response, HttpSession session, Locale locale) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");
        // 用户验证
        String validateCode = (String) session.getAttribute(CommonConstant.CAPTCHA);
        // 计算登录失败次数
        if (session.getAttribute(CommonConstant.ERROR_LOGIN_COUNT) == null) {
            session.setAttribute(CommonConstant.ERROR_LOGIN_COUNT, 0);
        }
        int errorNum = (Integer) session.getAttribute(CommonConstant.ERROR_LOGIN_COUNT);
        Map<String, Object> datas = new HashMap<String, Object>();
        String message = "";
        if (username == null || username.trim().length() <= 0 || password == null || password.length() <= 0) {
            errorNum++;
            message = messageSource.getMessage("info.login.nameOrPasswordEmpty", null, locale);
        } else if (errorNum >= 3 && !validateCode.equalsIgnoreCase(captcha)) {
            errorNum++;
            message = messageSource.getMessage("info.login.invalid.captcha", null, locale);
        } else {
            User user = userService.loginService(username, password);
            if (user != null) {
                session.setAttribute(CommonConstant.LOGIN_USER, user);
                // 移除错误登录计数
                if (session.getAttribute(CommonConstant.ERROR_LOGIN_COUNT) != null)
                    session.removeAttribute(CommonConstant.ERROR_LOGIN_COUNT);
                datas.put("error_num", 0);
                return successJSON("", datas);
            } else {
                errorNum++;
                message = messageSource.getMessage("info.login.error", null, locale);
            }
        }
        // 验证通过后清除SESSION验证码
        session.removeAttribute(CommonConstant.CAPTCHA);
        session.setAttribute(CommonConstant.ERROR_LOGIN_COUNT, errorNum);
        datas.put("error_num", errorNum);
        return errorJSON(message, datas);
    }

    // GET链接到登录页面
    @RequestMapping(value = "/signIn.html", method = RequestMethod.GET)
    public String signInGet(ModelMap model) {
        model.addAttribute("user", new User());
        return "login";
    }

    // POST处理登录
    @RequestMapping(value = "/signIn.html", method = RequestMethod.POST)
    public String signInPOST(@ModelAttribute("user") User user, String validateCode, HttpServletRequest request, HttpSession session, Errors errors) {
        // 验证用户信息
        String userName = user.getUserName();
        String password = user.getPassword();
        // 验证码
        String captcha = (String) session.getAttribute(CommonConstant.CAPTCHA);
        // 计算登录错误的次数
        if (session.getAttribute(CommonConstant.ERROR_LOGIN_COUNT) == null) {
            session.setAttribute(CommonConstant.ERROR_LOGIN_COUNT, 0);
        }
        int errorNum = (Integer) session.getAttribute(CommonConstant.ERROR_LOGIN_COUNT);
        if (userName == null || userName.trim().length() <= 0 || password == null || password.length() <= 0) {
            errors.reject("info.login.nameOrPasswordEmpty");
        } else if (errorNum >= 3) {
            if (validateCode != null && !validateCode.equalsIgnoreCase(captcha))
                errors.reject("info.login.invalid.captcha");
            else if (validateCode == null || validateCode.trim().length() <= 0)
                errors.reject("info.login.invalid.captchaEmpty");
        }
        if (errors.hasErrors()) {
            errorNum++;
            session.setAttribute(CommonConstant.ERROR_LOGIN_COUNT, errorNum);
            return "login";
        } else {
            User dbuser = userService.loginService(user.getUserName(), user.getPassword());
            if (dbuser != null) {
                if (session.getAttribute(CommonConstant.ERROR_LOGIN_COUNT) != null)
                    session.removeAttribute(CommonConstant.ERROR_LOGIN_COUNT);
                session.setAttribute(CommonConstant.LOGIN_USER, dbuser);
                String lastReqUrl = (String) session.getAttribute(CommonConstant.LAST_REQUEST_URL);
                if (lastReqUrl != null && lastReqUrl.trim().length() > 0 && !lastReqUrl.endsWith("signIn.html")) {
                    session.removeAttribute(CommonConstant.LAST_REQUEST_URL);
                    return "redirect:" + lastReqUrl;
                } else {
                    // 如果没有则重定向到index画面
                    return "redirect:index.html";
                }
            } else {
                errors.reject("info.login.error");
                errorNum++;
                session.setAttribute(CommonConstant.ERROR_LOGIN_COUNT, errorNum);
                return "login";
            }
        }
    }

    @RequestMapping(value = "/logout.html")
    public String logout(HttpServletRequest request, HttpSession session) {
        // 销毁session对象
        if (session.getAttribute(CommonConstant.LOGIN_USER) != null)
            session.invalidate();
        // 从请求的Header的REFERER属性中得到上一次请求的URL
        String lastReqUrl = request.getHeader("Referer");
        if (lastReqUrl != null && lastReqUrl.trim().length() > 0) {
            return "redirect:" + lastReqUrl;
        } else {
            // 如果没有则重定向到signIn画面
            return "redirect:signIn.html";
        }
    }

    @RequestMapping(value = "/changepwd_handle.html", method = RequestMethod.POST)
    public String changePWD(@ModelAttribute("userBean") ChangePasswordUserBean userBean, ModelMap model, HttpSession session, Errors errors, Locale locale) {
        // 使用ValidationUtils工具包验证数据的合法性
        String passwordLabel = messageSource.getMessage("page.label.changepwd.password", null, locale);
        String newPasswordLabel = messageSource.getMessage("page.label.changepwd.newpassword", null, locale);
        String confirmPasswordLabel = messageSource.getMessage("page.label.changepwd.confirmPassword", null, locale);
        ValidationUtils.rejectIfEmpty(errors, "password", "info.changepwd.required", new Object[]{passwordLabel});
        ValidationUtils.rejectIfEmpty(errors, "newPassword", "info.changepwd.required", new Object[]{newPasswordLabel});
        ValidationUtils.rejectIfEmpty(errors, "confirmPassword", "info.changepwd.required", new Object[]{confirmPasswordLabel});
        if (!errors.hasErrors()) {
            if (userBean.getNewPassword().length() < 6 || userBean.getNewPassword().length() > 20) {
                errors.rejectValue("newPassword", "info.changepwd.newpassword.invalid");
            } else if (!userBean.getConfirmPassword().equals(userBean.getNewPassword())) {
                errors.rejectValue("confirmPassword", "info.changepwd.confirmPassword.invalid");
            } else {
                // 判断用户输入的旧密码是否正确
                User user = (User) session.getAttribute(CommonConstant.LOGIN_USER);
                String hashPassword = SHA1Util.saltPassword(user.getSalt(), userBean.getPassword());
                if (user.getPassword().equals(hashPassword)) {
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

    @RequestMapping("/forget_pwd.html")
    public String forgetPassword() {
        return "forget_pwd";
    }

    @RequestMapping("/found_pwd.html")
    public String foundPassword(String email, ModelMap model, HttpServletRequest request, Locale locale) {
        if (email == null || email.trim().length() <= 0) {
            String emailLabel = messageSource.getMessage("page.label.foundPwd.email", null, locale);
            String errorInfo = messageSource.getMessage("info.required", new Object[]{emailLabel}, locale);
            model.addAttribute("errorInfo", errorInfo);
            return "forget_pwd";
        }
        if (!email.matches("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$")) {
            String emailLabel = messageSource.getMessage("page.label.foundPwd.email", null, locale);
            String errorInfo = messageSource.getMessage("info.invalid", new Object[]{emailLabel}, locale);
            model.addAttribute("errorInfo", errorInfo);
            model.addAttribute("email", email);
            return "forget_pwd";
        }

        // 验证数据库中是否存在指定邮箱
        User user = userService.validEmail(email);
        if (user == null) {
            String emailLabel = messageSource.getMessage("page.label.foundPwd.email", null, locale);
            String errorInfo = messageSource.getMessage("info.notexist", new Object[]{emailLabel}, locale);
            model.addAttribute("errorInfo", errorInfo);
            model.addAttribute("email", email);
            return "forget_pwd";
        }

        // 生成sid
        String sid = SHA1Util.generateSalt();
        Date date = new Date();
        long endTime = date.getTime() + 1800000;
        user.setSid(sid);
        user.setEndTime(endTime);
        userService.update(user);

        // 发送邮件给用户邮箱
        StringBuffer link = new StringBuffer("http://").append(request.getServerName());
        int port = request.getServerPort();
        if (port != 80)
            link.append(":").append(port);
        link.append(request.getContextPath()).append("/authenticate.html?sid=").append(sid).append("&uid=").append(user.getId());
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        String subject = messageSource.getMessage("email.foundpwd.title", null, locale);
        String content = messageSource.getMessage("email.foundpwd.content", new Object[]{user.getUserName(), dateStr, link.toString()}, locale);
        MailUtil.sendEMail(user.getEmail(), subject, content);

        model.addAttribute("email", email);
        return "found_pwd";
    }

    @RequestMapping("/authenticate.html")
    public String authenticate(String sid, Long uid, ModelMap model, HttpSession session) {
        if (sid == null || sid.trim().length() <= 0) {
            throw new HttpNotFoundException();
        }

        if (uid == null || uid <= 0) {
            throw new HttpNotFoundException();
        }

        User user = userService.getUser(uid);
        if (user == null) {
            throw new HttpNotFoundException();
        }

        // 检查失效
        Date now = new Date();
        if (now.getTime() > user.getEndTime()) {
            throw new HttpNotFoundException();
        }
        // 检查sid是否是有效的值
        if (!user.getSid().equals(sid)) {
            throw new HttpNotFoundException();
        }
        // 将链接设置过期
        user.setEndTime(0);
        userService.update(user);

        model.addAttribute("uid", uid);
        model.addAttribute("sid", sid);

        return "reset_pwd";
    }

    @RequestMapping(value = "/reset_pwd.html", method = RequestMethod.POST)
    public String resetPwd(String sid, Long uid, String newPassword, String confirmPassword, HttpSession session, ModelMap model, Locale locale) {
        List<String> errors = new ArrayList<String>();
        // 没有sid，则说明是非法的访问。
        if (sid == null || sid.trim().length() <= 0 || uid == null || uid <= 0) {
            String errorInfo = messageSource.getMessage("info.illegal.access", null, locale);
            errors.add(errorInfo);
            model.addAttribute("errorInfos", errors);
            return "reset_pwd";
        }

        User user = userService.getUser(uid);
        if (user == null || !sid.equals(user.getSid())) {
            String errorInfo = messageSource.getMessage("info.illegal.access", null, locale);
            errors.add(errorInfo);
            model.addAttribute("errorInfos", errors);
            return "reset_pwd";
        }

        // 验证密码的合法性
        if (newPassword == null || newPassword.equals("")) {
            String newPasswordLabel = messageSource.getMessage("page.label.changepwd.newpassword", null, locale);
            String errorInfo = messageSource.getMessage("info.required", new Object[]{newPasswordLabel}, locale);
            errors.add(errorInfo);
        } else if (newPassword.length() < 6 || newPassword.length() > 20) {
            String errorInfo = messageSource.getMessage("info.changepwd.newpassword.invalid", null, locale);
            errors.add(errorInfo);
        }
        if (confirmPassword == null || confirmPassword.equals("")) {
            String confirmPwdLabel = messageSource.getMessage("page.label.changepwd.confirmPassword", null, locale);
            String errorInfo = messageSource.getMessage("info.required", new Object[]{confirmPwdLabel}, locale);
            errors.add(errorInfo);
        } else if (!confirmPassword.equals(newPassword)) {
            String errorInfo = messageSource.getMessage("info.changepwd.confirmPassword.invalid", null, locale);
            errors.add(errorInfo);
        }

        if (errors.size() > 0) {
            // 如果有错误则给予提示信息
            model.addAttribute("uid", uid);
            model.addAttribute("sid", sid);

            model.addAttribute("newPassword", newPassword);
            model.addAttribute("confirmPassword", confirmPassword);
            model.addAttribute("errorInfos", errors);
            return "reset_pwd";
        } else {
            user.setLastLogin(new Date());
            user.setSid("");
            userService.update(user);
            userService.changePassword(user, newPassword);
            session.setAttribute(CommonConstant.LOGIN_USER, user);
            // 修改成功后，重定向首页
            return "redirect:index.html";
        }
    }
}
