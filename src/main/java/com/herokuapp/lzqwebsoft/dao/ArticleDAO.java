package com.herokuapp.lzqwebsoft.dao;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.herokuapp.lzqwebsoft.pojo.Article;

@Repository("articleDAO")
public class ArticleDAO extends BaseDAO {
	
	public void save(Article article) {
		getHibernateTemple().save(article);
	}
	
	public void update(Article article) {
		Session session = getHibernateTemple().getSessionFactory().getCurrentSession();
		session.beginTransaction();
		session.merge(article);
		article = (Article)session.get(Article.class, article.getId());
		session.update(article);
		session.getTransaction().commit();
	}
	
	public Article getArticleById(String id) {
		return getHibernateTemple().get(Article.class, id);
	}
	
	public void delete(Article article) {
		getHibernateTemple().delete(article);
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> getAllArticle() {
		String queryString = "from Article";
		return (List<Article>)getHibernateTemple().find(queryString);
	}
}
