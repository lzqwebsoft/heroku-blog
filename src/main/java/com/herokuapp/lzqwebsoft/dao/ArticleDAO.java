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
		getHibernateTemple().update(article);
	}
	
	public void edit(Article article) {
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
		String queryString = "from Article as art where art.status=1";
		return (List<Article>)getHibernateTemple().find(queryString);
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> getAllDraft() {
		String queryString = "from Article as art where art.status=0";
		return (List<Article>)getHibernateTemple().find(queryString);
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> selectArticleByTypeAndTitle(int typeId, String title) {
		String queryString = "";
		String likeParam = new StringBuffer("%").append(title).append("%").toString();
		List<Article> articles = null;
		if(typeId==0) {
			queryString = "from Article as art where art.status=1 and art.title like ?";
			articles = (List<Article>)getHibernateTemple().find(queryString, new Object[]{likeParam});
		} else {
			queryString = "from Article as art where art.status=1 and art.type.id=? and art.title like ?";
			articles = (List<Article>)getHibernateTemple().find(queryString, new Object[]{typeId, likeParam});
		}
		return articles;
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> selectArticleByTypeId(int typeId) {
		String queryString = "from Article as art where art.status=1 and art.type.id=?";
		return (List<Article>)getHibernateTemple().find(queryString, new Object[]{typeId});
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> seletArticleTop10(){
		String queryString = "from Article as art where art.status=1 order by art.readedNum desc";
		return (List<Article>)getHibernateTemple().find(queryString);
	}
}
