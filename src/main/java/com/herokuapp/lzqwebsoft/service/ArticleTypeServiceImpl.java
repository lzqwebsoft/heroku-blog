package com.herokuapp.lzqwebsoft.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.herokuapp.lzqwebsoft.dao.ArticleTypeDAO;
import com.herokuapp.lzqwebsoft.pojo.ArticleType;

@Service("articleTypeService")
public class ArticleTypeServiceImpl implements ArticleTypeService {
	
	@Autowired
	private ArticleTypeDAO articleTypeDAO;

	@Override
	public List<ArticleType> getAllArticleType() {
		return articleTypeDAO.getAllArticleType();
	}

	@Override
	public ArticleType save(ArticleType type) {
		type.setCreateAt(new Date());
		type.setUpdateAt(new Date());
		return articleTypeDAO.save(type);
	}
}
