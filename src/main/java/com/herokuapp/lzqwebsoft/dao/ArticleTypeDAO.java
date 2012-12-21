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
	
	public void delete(ArticleType articleType) {
		getHibernateTemple().delete(articleType);
	}
	
	public ArticleType getArticleTypeById(int id) {
		return getHibernateTemple().get(ArticleType.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<ArticleType> getAllArticleType() {
		String queryString = "from ArticleType";
		return getHibernateTemple().find(queryString);
	}
	
	@SuppressWarnings("unchecked")
	public ArticleType getArticleTypeByName(String name) {
		String queryString = "from ArticleType art where art.name=?";
		List<ArticleType> list = (List<ArticleType>)getHibernateTemple().find(queryString, new Object[]{name});
		if(list!=null&&list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
	}
}
