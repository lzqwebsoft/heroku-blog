package com.herokuapp.lzqwebsoft.controller;

import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.herokuapp.lzqwebsoft.pojo.ArticleType;
import com.herokuapp.lzqwebsoft.service.ArticleTypeService;

@Controller
@RequestMapping("/article_type")
public class ArticleTypeController {
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ArticleTypeService articleTypeService;
	
	// 类型管理操作
	@RequestMapping(value="/add")
	public String add(String article_type_name,
			HttpServletRequest request, Locale locale) {
		String type_name_label = messageSource.getMessage("page.label.articleTypeName", null, locale);
		if(article_type_name==null||article_type_name.trim().length()<=0) {
			String errorMessage = messageSource.getMessage("info.articleType.empty",
					new Object[]{type_name_label}, locale);
			request.setAttribute("errorInfo", errorMessage);
		} else {
			// 将用户添加的文章类型入库
			ArticleType articleType = new ArticleType();
			articleType.setName(article_type_name);
			articleType.setDisable(false);
			articleTypeService.save(articleType);
			// 插入成功后，遍历数据库得到所有文章类型
			List<ArticleType> articleTypes = articleTypeService.getAllArticleType();
			request.setAttribute("articleTypes", articleTypes);
		}
		return "_article_type_tab";
	}
	
	
	@RequestMapping(value="/delete/{articleTypeId}")
	public void deleteArticleType(@PathVariable("articleTypeId")String articleTypeId,
			HttpServletResponse response, Locale locale) {
		
	}
}
