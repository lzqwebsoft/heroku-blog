package com.herokuapp.lzqwebsoft.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.herokuapp.lzqwebsoft.pojo.Article;
import com.herokuapp.lzqwebsoft.pojo.ArticlePattern;
import com.herokuapp.lzqwebsoft.pojo.ArticleType;
import com.herokuapp.lzqwebsoft.pojo.User;
import com.herokuapp.lzqwebsoft.service.ArticleService;
import com.herokuapp.lzqwebsoft.service.ArticleTypeService;
import com.herokuapp.lzqwebsoft.util.CommonConstant;

@Controller
@RequestMapping("/article")
public class ArticleController {
	@Autowired
	private ArticleTypeService articleTypeService;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private ArticlePattern patterns;
	
	@RequestMapping("/new")
	public String create(ModelMap model) {
		Article article = new Article();
		article.setAllowComment(true);
		model.addAttribute("article", article);
		
		model.addAttribute("patterns", patterns);
		
		List<ArticleType> articleTypes = articleTypeService.getAllArticleType();
		model.addAttribute("articleTypes", articleTypes);
		return "new";
	}
	
	@RequestMapping("/publish")
	public String publish(@ModelAttribute("article")Article article, int type_model,
			String new_type, ModelMap model, HttpSession session) {
		// 如果为添加类型，则先创建一个新的类型
		boolean modelNew = false;
		if(type_model==1) {
			if(new_type!=null&&new_type.trim().length()>0) {
				modelNew = true;
			}
		}
		// 添加作者
		User user = (User)session.getAttribute(CommonConstant.LOGIN_USER);
		article.setAuthor(user);
		// 入库
		articleService.save(article, new_type, modelNew);
		
        model.addAttribute("patterns", patterns);
		List<ArticleType> articleTypes = articleTypeService.getAllArticleType();
		model.addAttribute("articleTypes", articleTypes);
		return "new";
	}

}
