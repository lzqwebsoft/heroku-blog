package com.herokuapp.lzqwebsoft.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.herokuapp.lzqwebsoft.pojo.ArticleType;

@Repository("articleTypeDAO")
public class ArticleTypeDAO extends BaseDAO {
	
	public ArticleType save(ArticleType articleType) {
		getHibernateTemple().save(articleType);
		return articleType;
	}
	
	public void update(ArticleType articleType) {
		getHibernateTemple().update(articleType);
	}
	
	@SuppressWarnings("unchecked")
	public List<ArticleType> getAllArticleType() {
		String queryString = "from ArticleType";
		return getHibernateTemple().find(queryString);
	}
}
