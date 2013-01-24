package com.herokuapp.lzqwebsoft.controller;

import java.util.ArrayList;
import java.util.List;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.herokuapp.lzqwebsoft.pojo.Article;
import com.herokuapp.lzqwebsoft.pojo.ArticlePattern;
import com.herokuapp.lzqwebsoft.pojo.ArticleType;
import com.herokuapp.lzqwebsoft.pojo.Comment;
import com.herokuapp.lzqwebsoft.pojo.Page;
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
	private MessageSource messageSource;
	
	@Autowired
	private ArticlePattern patterns;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/show/{articleId}")
	public String show(@PathVariable("articleId")String articleId,
			HttpSession session, ModelMap model) {
		Article article = articleService.get(articleId);
		if(article==null)
			return "redirect:/error404.html";
		model.addAttribute("article", article);
		
		// 阅读计数, 只有是没有登录的用户才进行记数
		if(session.getAttribute(CommonConstant.LOGIN_USER)==null) {
			List<String> viewedArticles = (List<String> )session.getAttribute(CommonConstant.VIEWED_ARTICLES);
			if(viewedArticles==null) {
				viewedArticles = new ArrayList<String>();
				viewedArticles.add(articleId);
				articleService.addViewedCount(article);
				session.setAttribute(CommonConstant.VIEWED_ARTICLES, viewedArticles);
			} else {
				boolean viewed = false;
				for(String viewedArticle : viewedArticles) {
					if(viewedArticle.equals(articleId)) {
						viewed = true;
						break;
					}
				}
				if(!viewed) {
					viewedArticles.add(articleId);
					articleService.addViewedCount(article);
					session.setAttribute(CommonConstant.VIEWED_ARTICLES, viewedArticles);
				}
			}
		}
		
		List<Comment> comments = commentService.getAllParentComment(articleId);
		model.addAttribute("comments", comments);
		
		// 上篇文章
		Article previousArticle = articleService.getPreviousArticle(article);
		model.addAttribute("previousArticle", previousArticle);
		// 下篇文章
		Article nextArticle = articleService.getNextArticle(article);
		model.addAttribute("nextArticle", nextArticle);
		// 5篇关联的文章
		List<Article> ass_articles = articleService.getAssociate5Articles(article);
		model.addAttribute("associate5Articles", ass_articles);
		
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
	public String publish(@ModelAttribute("article")Article article, Errors errors, 
			String type_model, String new_type, ModelMap model, HttpServletRequest request,
			HttpSession session, String publish, String save, String editOrCreate, Locale locale) {
		// 验证用户提交数据的合法性
		String articleContentLabel = messageSource.getMessage("page.label.article.content", null, locale);
		String articleTitleLabel = messageSource.getMessage("page.label.title", null, locale);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "info.required", new Object[]{articleTitleLabel});
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "content", "info.required", new Object[]{articleContentLabel});
		int patternTypeId = article.getPatternTypeId();
		if(patternTypeId==0) {
			String patternLabel = messageSource.getMessage("page.label.pattern", null, locale);
			errors.rejectValue("patternTypeId", "info.select", new Object[]{patternLabel}, "");
		}
		
		int type = 0;
		// 文章类型添加的方式，为0表示通过下拉框选择，1表示再创建
		try {
			type = Integer.parseInt(type_model);
		} catch(NumberFormatException e) {
			type = 0;
		}
		
		if(type==0) {
			// 当为选择一个类别时
			ArticleType articleType = article.getType();
			if(articleType==null||articleType.getId()==0) {
				String articleTypeLabel = messageSource.getMessage("page.label.article.type", null, locale);
				errors.rejectValue("type.id", "info.select", new Object[]{articleTypeLabel}, "");
			} else {
				articleType = articleTypeService.get(articleType.getId());
				if(articleType==null) {
					errors.rejectValue("type", "info.select.articleType.notExist");
				}
			}
		} else if (type==1) {
			// 当为创建一个类别时
			if(new_type==null||new_type.trim().length()<=0) {
				String articleTypeLabel = messageSource.getMessage("page.label.article.type", null, locale);
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "type.name", "info.required", new Object[]{articleTypeLabel});
			}
		}
		
		// 判断是否有错误
		if(errors.hasErrors()) {
			List<ArticleType> articleTypes = articleTypeService.getAllArticleType();
			model.addAttribute("articleTypes", articleTypes);
			
			model.addAttribute("patterns", patterns);
			return "new";
		} else {
			// 如果为添加类型，则先创建一个新的类型
			boolean modelNew = false;
			if(type==1) {
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
			return "redirect:/show/"+article.getId()+".html";
		}
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
	public void delete(@PathVariable("articleId")String articleId, String url,
			String pageNo, HttpServletResponse response) {
		articleService.delete(articleId);
		response.setStatus(HttpServletResponse.SC_OK);
	}
	
	//================== 主要用于set页面的AJAX处理=============
	@RequestMapping("/delete/article/{articleId}")
	public String deleteArticle(@PathVariable("articleId")String articleId, int articleTypeId,
			String title, String pageNo, HttpServletRequest request) {
		if(pageNo==null||pageNo.trim().length()<=0)
			pageNo = "1";
		int pageNoIndex = Integer.parseInt(pageNo);
		
		articleService.delete(articleId);
		Page<Article> articles = articleService.getArticleByTypeAndTitle(articleTypeId, title, pageNoIndex, 15);
		
		// 如果删除的一条数据刚好是这一页的最后一条数据，则显示上页
		if(pageNoIndex>1&&articles.getData().size()<=0)
			articles = articleService.getArticleByTypeAndTitle(articleTypeId, title, pageNoIndex-1, 15);
		
		request.setAttribute("page", articles);
		
		request.setAttribute("articleTypeId", articleTypeId);
		request.setAttribute("title", title);
		
		return "_article_tab";
	}
	
	@RequestMapping("/delete/draft/{articleId}")
	public String deleteDraft(@PathVariable("articleId")String articleId,
			String pageNo, HttpServletRequest request) {
		if(pageNo==null||pageNo.trim().length()<=0)
			pageNo = "1";
		int pageNoIndex = Integer.parseInt(pageNo);
		
		articleService.delete(articleId);
		// 所有的草稿
		Page<Article> page_drafts =  articleService.getAllDrafts(pageNoIndex, 15);
		
		// 如果删除的一条数据刚好是这一页的最后一条数据，则显示上页
		if(pageNoIndex>1&&page_drafts.getData().size()<=0)
			page_drafts = articleService.getAllDrafts(pageNoIndex-1, 15);
			
		request.setAttribute("page_drafts", page_drafts);
		
		return "_draft_tab";
	}
	
	@RequestMapping("/draft/page")
	public String pageDraft(String pageNo, HttpServletRequest request) {
		if(pageNo==null||pageNo.trim().length()<=0)
			pageNo = "1";
		int pageNoIndex = Integer.parseInt(pageNo);
		
		// 所有的草稿
		Page<Article> page_drafts =  articleService.getAllDrafts(pageNoIndex, 15);
		request.setAttribute("page_drafts", page_drafts);
		
		return "_draft_tab";
	}
	
	@RequestMapping("/article/select")
	public String select(int articleTypeId, String title,
			String pageNo, HttpServletRequest request){
		if(pageNo==null||pageNo.trim().length()<=0)
			pageNo = "1";
		int pageNoIndex = Integer.parseInt(pageNo);
		
		Page<Article> page = articleService.getArticleByTypeAndTitle(articleTypeId, title, pageNoIndex, 15);
		request.setAttribute("page", page);
		
		request.setAttribute("articleTypeId", articleTypeId);
		request.setAttribute("title", title);
		
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
