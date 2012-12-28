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
import com.herokuapp.lzqwebsoft.pojo.Comment;
import com.herokuapp.lzqwebsoft.pojo.User;
import com.herokuapp.lzqwebsoft.service.ArticleService;
import com.herokuapp.lzqwebsoft.service.ArticleTypeService;
import com.herokuapp.lzqwebsoft.service.CommentService;
import com.herokuapp.lzqwebsoft.util.CommonConstant;

@Controller
public class ArticleController {
	@Autowired
	private ArticleTypeService articleTypeService;
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private ArticlePattern patterns;
	
	@RequestMapping(value="/show/{articleId}")
	public String show(@PathVariable("articleId")String articleId,
			ModelMap model) {
		Article article = articleService.get(articleId);
		model.addAttribute("article", article);
		
		List<Comment> comments = commentService.getAllParentComment(articleId);
		model.addAttribute("comments", comments);
		
		Comment comment = new Comment();
		comment.setArticle(article);
		model.addAttribute("comment", comment);
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
	
	//================== 主要用于set页面的AJAX处理=============
	@RequestMapping("/delete/article/{articleId}")
	public String deleteArticle(@PathVariable("articleId")String articleId,
			HttpServletRequest request) {
		articleService.delete(articleId);
		List<Article> articles = articleService.getAllAricleWithoutContent();
		request.setAttribute("articles", articles);
		// 所有的文章类型
		List<ArticleType> articleTypes = articleTypeService.getAllArticleType();
		request.setAttribute("articleTypes", articleTypes);
		
		return "_article_tab";
	}
	
	@RequestMapping("/delete/draft/{articleId}")
	public String deleteDraft(@PathVariable("articleId")String articleId,
			HttpServletRequest request) {
		articleService.delete(articleId);
		// 所有的草稿
		List<Article> drafts =  articleService.getAllDrafts();
		request.setAttribute("drafts", drafts);
		
		return "_draft_tab";
	}
	
	@RequestMapping("/article/select")
	public String select(int articleTypeId, String title, HttpServletRequest request){
		List<Article> articles = articleService.getArticleByTypeAndTitle(articleTypeId, title);
		request.setAttribute("articles", articles);
		
		// 所有的文章类型
		List<ArticleType> articleTypes = articleTypeService.getAllArticleType();
		request.setAttribute("articleTypes", articleTypes);
		return "_article_tab";
	}
	
	@RequestMapping("/update/allow_comment/{articleId}")
	public void updateAllowComment(@PathVariable("articleId")String articleId,
			boolean allowComment, HttpServletResponse response) {
		articleService.updateAllowComment(articleId, allowComment);
		response.setStatus(HttpServletResponse.SC_OK);
	}
	
	@RequestMapping("/update/is_top/{articleId}")
	public void updateIsTop(@PathVariable("articleId")String articleId,
			boolean isTop, HttpServletResponse response) {
		articleService.updateIsTop(articleId, isTop);
		response.setStatus(HttpServletResponse.SC_OK);
	}
}
