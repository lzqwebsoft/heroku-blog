package com.herokuapp.lzqwebsoft.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.herokuapp.lzqwebsoft.util.MakeCertPic;
import com.herokuapp.lzqwebsoft.util.CommonConstant;

/**
 * 生成图片验证码的Servlet
 * 
 * @author johnny
 * 
 */
public class CaptchaServlet extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        response.setHeader("Pragma", "No-cache");  
        response.setHeader("Cache-Control", "No-cache");  
        response.setDateHeader("Expires", 0);  
        //指定生成的响应图片  
        response.setContentType("image/jpeg");
        
        MakeCertPic certPic = new MakeCertPic();
        String certString = certPic.getCertPic(100, 30, response.getOutputStream());
        
        HttpSession session = request.getSession(true);
        //设置session对象5分钟失效
//        session.setMaxInactiveInterval(5*60);
        session.setAttribute(CommonConstant.CAPTCHA, certString);
    }
}
