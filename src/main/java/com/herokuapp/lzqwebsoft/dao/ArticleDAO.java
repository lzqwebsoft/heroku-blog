package com.herokuapp.lzqwebsoft.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.herokuapp.lzqwebsoft.pojo.Article;
import com.herokuapp.lzqwebsoft.pojo.Page;

@Repository("articleDAO")
public class ArticleDAO extends PageBaseDAO<Article> {
	
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
	public Page<Article> getAllArticle(int pageNo, int pageSize) {
		String queryString = "from Article as art where art.status=1 order by art.isTop desc, art.createAt desc";
		return pagedQuery(queryString, pageNo, pageSize);
	}
	
	@SuppressWarnings("unchecked")
	public Page<Article> getAllDraft(int pageNo, int pageSize) {
		String queryString = "from Article as art where art.status=0 order by art.isTop desc, art.createAt desc";
		return pagedQuery(queryString, pageNo, pageSize);
	}
	
	@SuppressWarnings("unchecked")
	public Page<Article> selectArticleByTypeAndTitle(int typeId, String title, int pageNo, int pageSize) {
		String queryString = "";
		String likeParam = new StringBuffer("%").append(title).append("%").toString();
		Page<Article> page = new Page<Article>();
		if(typeId==0) {
			queryString = "from Article as art where art.status=1 and art.title like ? order by art.isTop desc, art.createAt desc";
			page = pagedQuery(queryString, pageNo, pageSize, new Object[]{likeParam});
		} else {
			queryString = "from Article as art where art.status=1 and art.type.id=? and art.title like ? order by art.isTop desc, art.createAt desc";
			page = pagedQuery(queryString, pageNo, pageSize, new Object[]{typeId, likeParam});
		}
		return page;
	}
	
	@SuppressWarnings("unchecked")
	public Page<Article> selectArticleByTypeId(int typeId, int pageNo, int pageSize) {
		String queryString = "from Article as art where art.status=1 and art.type.id=? order by art.isTop desc, art.createAt desc";
		return pagedQuery(queryString, pageNo, pageSize, typeId);
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> seletArticleTop10(){
		final String queryString = "from Article as art where art.status=1 order by art.readedNum desc";
		return (List<Article>) getHibernateTemple().executeFind(
				new HibernateCallback() {
					public List<Article> doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(queryString);
						query.setFirstResult(0);
						query.setMaxResults(10);
						List list = query.list();
						return list;
					}
				});
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> getAssociate5Articles(final Article article) {
		final String queryString = "from Article as art where art.status=1 and art.id!=? and art.type.id=? order by art.createAt desc";
		return (List<Article>) getHibernateTemple().executeFind(
				new HibernateCallback() {
					public List<Article> doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(queryString);
						query.setParameter(0, article.getId());
						query.setParameter(1, article.getType().getId());
						query.setFirstResult(0);
						query.setMaxResults(5);
						List list = query.list();
						return list;
					}
				});
	}
	
	@SuppressWarnings("unchecked")
	public Article getPreviousArticle(final Article article) {
		final String queryString = "from Article as art where art.createAt<? and art.status=1 order by art.createAt desc";
		List<Article> list =  (List<Article>) getHibernateTemple().executeFind(
				new HibernateCallback() {
					public List<Article> doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(queryString);
						query.setParameter(0, article.getCreateAt());
						query.setFirstResult(0);
						query.setMaxResults(1);
						List list = query.list();
						return list;
					}
				});;
		if(list!=null&&list.size()>=1)
			return list.get(0);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public Article getNextArticle(final Article article) {
		final String queryString = "from Article as art where art.createAt>? and art.status=1 order by art.createAt asc";
		List<Article> list =  (List<Article>) getHibernateTemple().executeFind(
				new HibernateCallback() {
					public List<Article> doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(queryString);
						query.setParameter(0, article.getCreateAt());
						query.setFirstResult(0);
						query.setMaxResults(1);
						List list = query.list();
						return list;
					}
				});;
		if(list!=null&&list.size()>=1)
			return list.get(0);
		return null;
	}
}
