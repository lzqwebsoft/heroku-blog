package com.herokuapp.lzqwebsoft.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.herokuapp.lzqwebsoft.pojo.BlogInfo;
import com.herokuapp.lzqwebsoft.pojo.ChangePasswordUserBean;
import com.herokuapp.lzqwebsoft.service.BlogInfoService;

@Controller
public class HomeController{
	@Autowired
	private BlogInfoService blogInfoService;
	
	@RequestMapping(value="/index")
	public String home() {
		return "index";
	}
	
	@RequestMapping(value="/show/{articleId}")
	public String show(@PathVariable("articleId")String articleId) {
	    return "show";
	}
	
	@RequestMapping(value="/new") 
	public String newArticle(){
	    return "new";
	}
	
	@RequestMapping(value="/change_password")
    public String changePassword(ModelMap model){
		model.addAttribute("userBean", new ChangePasswordUserBean());
        return "change_password";
    }
	
	@RequestMapping(value="/about")
	public void about(HttpServletResponse response) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("text/html;charset=UTF-8");
			BlogInfo blogInfo = blogInfoService.getSystemBlogInfo();
			
			out.print(blogInfo.getAbout());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			if(out!=null) {
				out.close();
			}
		}
	}
}