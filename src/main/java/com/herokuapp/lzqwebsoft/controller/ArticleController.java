package com.herokuapp.lzqwebsoft.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.herokuapp.lzqwebsoft.pojo.Article;
import com.herokuapp.lzqwebsoft.pojo.ArticlePattern;
import com.herokuapp.lzqwebsoft.pojo.ArticleType;
import com.herokuapp.lzqwebsoft.pojo.User;
import com.herokuapp.lzqwebsoft.service.ArticleService;
import com.herokuapp.lzqwebsoft.service.ArticleTypeService;
import com.herokuapp.lzqwebsoft.util.CommonConstant;

@Controller
public class ArticleController {
	@Autowired
	private ArticleTypeService articleTypeService;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private ArticlePattern patterns;
	
	@RequestMapping(value="/show/{articleId}")
	public String show(@PathVariable("articleId")String articleId, ModelMap model) {
		Article article = articleService.get(articleId);
		model.addAttribute("article", article);
	    return "show";
	}
	
	@RequestMapping("/article/new")
	public String create(ModelMap model) {
		Article article = new Article();
		article.setAllowComment(true);
		model.addAttribute("article", article);
		
		model.addAttribute("patterns", patterns);
		
		model.addAttribute("editOrCreate", "CREATE");
		
		List<ArticleType> articleTypes = articleTypeService.getAllArticleType();
		model.addAttribute("articleTypes", articleTypes);
		return "new";
	}
	
	@RequestMapping("/article/publish")
	public String publish(@ModelAttribute("article")Article article, int type_model,
			String new_type, ModelMap model, HttpServletRequest request,
			HttpSession session, String publish, String save, String editOrCreate) {
		// 如果为添加类型，则先创建一个新的类型
		boolean modelNew = false;
		if(type_model==1) {
			if(new_type!=null&&new_type.trim().length()>0) {
				modelNew = true;
			}
		}
		boolean isDraft = false;
		if(publish!=null&&publish.trim().length()>0) {
			isDraft = false;
		} else {
			isDraft = true;
		}
		// 添加作者
		User user = (User)session.getAttribute(CommonConstant.LOGIN_USER);
		article.setAuthor(user);
		// 入库
		if(editOrCreate!=null&&editOrCreate.equals("EDIT")) {
			articleService.update(article, new_type, modelNew, isDraft);
		} else {
			articleService.save(article, new_type, modelNew, isDraft);
		}
		
        model.addAttribute("patterns", patterns);
		List<ArticleType> articleTypes = articleTypeService.getAllArticleType();
		model.addAttribute("articleTypes", articleTypes);
		return "redirect:/show/"+article.getId()+".html";
	}
	
	@RequestMapping("/edit/{articleId}")
	public String edit(@PathVariable("articleId")String articleId, ModelMap model) {
		Article article = articleService.get(articleId);
		model.addAttribute("article", article);
		
        model.addAttribute("patterns", patterns);
        model.addAttribute("editOrCreate", "EDIT");
		
		List<ArticleType> articleTypes = articleTypeService.getAllArticleType();
		model.addAttribute("articleTypes", articleTypes);
		
		return "new";
	}
	
	@RequestMapping("/delete/{articleId}")
	public void delete(@PathVariable("articleId")String articleId,
			HttpServletResponse response) {
		articleService.delete(articleId);
		response.setStatus(HttpServletResponse.SC_OK);
	}

}
