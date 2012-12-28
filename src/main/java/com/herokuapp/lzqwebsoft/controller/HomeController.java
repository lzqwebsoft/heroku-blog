package com.herokuapp.lzqwebsoft.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.herokuapp.lzqwebsoft.pojo.Article;
import com.herokuapp.lzqwebsoft.pojo.ArticleType;
import com.herokuapp.lzqwebsoft.pojo.BlogInfo;
import com.herokuapp.lzqwebsoft.pojo.ChangePasswordUserBean;
import com.herokuapp.lzqwebsoft.service.ArticleService;
import com.herokuapp.lzqwebsoft.service.ArticleTypeService;
import com.herokuapp.lzqwebsoft.service.BlogInfoService;

@Controller
public class HomeController{
	@Autowired
	private BlogInfoService blogInfoService;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleTypeService articleTypeService;
	
	@RequestMapping(value="/index")
	public String home(ModelMap model) {
		List<Article> articles = articleService.getAllAricle();
		model.addAttribute("articles", articles);
		
		// 阅读排行榜的前10篇博文
		List<Article> top10Articles = articleService.getReadedTop10();
		model.addAttribute("top10Articles", top10Articles);
		
		List<ArticleType> articleTypes = articleTypeService.getAllArticleType();
		model.addAttribute("articleTypes", articleTypes);
		return "index";
	}
	
	@RequestMapping(value="/select/{articleTypeId}")
	public String select(@PathVariable("articleTypeId")int articleTypeId,
			ModelMap model) {
		List<Article> articles = articleService.getArticleByTypeId(articleTypeId);
		model.addAttribute("articles", articles);
		
		List<Article> top10Articles = articleService.getReadedTop10();
		model.addAttribute("top10Articles", top10Articles);
		
		List<ArticleType> articleTypes = articleTypeService.getAllArticleType();
		model.addAttribute("articleTypes", articleTypes);
		return "index";
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