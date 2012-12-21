package com.herokuapp.lzqwebsoft.dao;

import org.springframework.stereotype.Repository;

import com.herokuapp.lzqwebsoft.pojo.Article;

@Repository("articleDAO")
public class ArticleDAO extends BaseDAO {
	
	public void save(Article article) {
		getHibernateTemple().save(article);
	}
	
	public Article getArticleById(String id) {
		return getHibernateTemple().get(Article.class, id);
	}
}
