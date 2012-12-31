package com.herokuapp.lzqwebsoft.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.herokuapp.lzqwebsoft.pojo.Article;
import com.herokuapp.lzqwebsoft.pojo.ArticleType;
import com.herokuapp.lzqwebsoft.pojo.BlogInfo;
import com.herokuapp.lzqwebsoft.pojo.Image;
import com.herokuapp.lzqwebsoft.pojo.Page;
import com.herokuapp.lzqwebsoft.service.ArticleService;
import com.herokuapp.lzqwebsoft.service.ArticleTypeService;
import com.herokuapp.lzqwebsoft.service.BlogInfoService;
import com.herokuapp.lzqwebsoft.service.ImageService;

/**
 * 主要控制用户对博客的设置操作
 * @author zqluo
 */
@Controller
public class SetController {
	@Autowired
	private BlogInfoService blogInfoService;
	
	@Autowired
	private ArticleTypeService articleTypeService;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private ImageService imageSerivce;
	
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping(value="/set")
	public String set(HttpServletRequest request, ModelMap model) {
		BlogInfo blogInfo = blogInfoService.getSystemBlogInfo();
		if(blogInfo==null)
			blogInfo = new BlogInfo();
		model.addAttribute(blogInfo);
		// 所有的文章
		Page<Article> page =  articleService.getAllAricleWithoutContent(1, 15);
		request.setAttribute("page", page);
		
		request.setAttribute("articleTypeId", 0);
		request.setAttribute("title", "");
		// 所有的文章类型
		List<ArticleType> articleTypes = articleTypeService.getAllArticleType();
		request.setAttribute("articleTypes", articleTypes);
		// 所有的图片
		List<Image> images = imageSerivce.getAllImages();
		request.setAttribute("images", images);
		// 所有的草稿
		List<Article> drafts =  articleService.getAllDrafts();
		request.setAttribute("drafts", drafts);
		
		return "set";
	}
	
	// 控制博客配置信息
	@RequestMapping(value="/handleInfo")
	public void configBlogInfo(BlogInfo blogInfo,
			HttpServletResponse response, Locale locale) {
		// 验证数据
		String head = blogInfo.getHead();
		String headLabel = messageSource.getMessage("page.label.head", null, locale);
//		String descriptions = blogInfo.getDescriptions();
//		String descriptionsLabel = messageSource.getMessage("page.label.descriptions", null, locale);
		String email = blogInfo.getEmail();
		String emailLabel = messageSource.getMessage("page.label.email", null, locale);
		String about = blogInfo.getAbout();
		String aboutLabel = messageSource.getMessage("page.label.about", null, locale);
		List<String> errors = new ArrayList<String>();
		if(head==null||head.trim().length()<=0) {
			errors.add(messageSource.getMessage("info.blogInfo.required", new Object[]{headLabel}, locale));
		}
		if(email==null||email.trim().length()<=0) {
			errors.add(messageSource.getMessage("info.blogInfo.required", new Object[]{emailLabel}, locale));
		}
		if(about==null||about.trim().length()<=0) {
			errors.add(messageSource.getMessage("info.blogInfo.required", new Object[]{aboutLabel}, locale));
		}
		PrintWriter out = null;
		try {
			out = response.getWriter();
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("application/json;charset=UTF-8");
			StringBuffer json = new StringBuffer();
			if(errors.size()>0) {
				json.append("{\"status\": \"FAILURE\", \"messages\": [");
				for(String error: errors)
					json.append("\"").append(error).append("\",");
				// 去掉最后一个逗号
				json.deleteCharAt(json.length()-1);
				json.append("]}");
			} else {
				String successMessage = messageSource.getMessage("info.blogInfo.save.success", null, locale);
				json.append("{\"status\": \"SUCCESS\", \"messages\": \"").append(successMessage).append("\"}");
				blogInfoService.updateBlogInfo(blogInfo);
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
}
