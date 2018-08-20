package com.herokuapp.lzqwebsoft.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.herokuapp.lzqwebsoft.pojo.Article;
import com.herokuapp.lzqwebsoft.pojo.Page;

@Repository("articleDAO")
public class ArticleDAO extends PageBaseDAO<Article> {

    public void save(Article article) {
        getSession().save(article);
    }

    public void update(Article article) {
        getSession().update(article);
    }

    public void edit(Article article) {
        Session session = getSession();
        session.merge(article);
        article = (Article) session.get(Article.class, article.getId());
        session.update(article);
    }

    public Article getArticleById(String id) {
        return getSession().get(Article.class, id);
    }

    public void delete(Article article) {
        getSession().clear(); // 这里必须的，清除缓存，不然因为与article type关联删除会报错
        getSession().delete(article);
    }

    public Page<Article> getAllArticle(int pageNo, int pageSize) {
        String queryString = "from Article as art where art.status=1 order by art.isTop desc, art.createAt desc";
        return pagedQuery(queryString, pageNo, pageSize);
    }

    public Page<Article> getAllDraft(int pageNo, int pageSize) {
        String queryString = "from Article as art where art.status=0 order by art.isTop desc, art.createAt desc";
        return pagedQuery(queryString, pageNo, pageSize);
    }

    public Page<Article> selectArticleByTypeAndTitle(int typeId, String title, int pageNo, int pageSize) {
        String queryString = "";
        String likeParam = new StringBuffer("%").append(title).append("%").toString();
        Page<Article> page = new Page<Article>();
        if (typeId == 0) {
            queryString = "from Article as art where art.status=1 and art.title like ?0 order by art.isTop desc, art.createAt desc";
            page = pagedQuery(queryString, pageNo, pageSize, likeParam);
        } else {
            queryString = "from Article as art where art.status=1 and art.type.id=?0 and art.title like ?1 order by art.isTop desc, art.createAt desc";
            page = pagedQuery(queryString, pageNo, pageSize, typeId, likeParam);
        }
        return page;
    }

    public Page<Article> selectArticleByTypeId(int typeId, int pageNo, int pageSize) {
        String queryString = "from Article as art where art.status=1 and art.type.id=?0 order by art.isTop desc, art.createAt desc";
        return pagedQuery(queryString, pageNo, pageSize, typeId);
    }

    @SuppressWarnings("unchecked")
    public List<Article> seletArticleTop10() {
        final String queryString = "from Article as art where art.status=1 order by art.readedNum desc";
        return (List<Article>) getSession().createQuery(queryString).setFirstResult(0).setMaxResults(10).list();
    }

    @SuppressWarnings("unchecked")
    public List<Article> getAssociate5Articles(final Article article) {
        final String queryString = "from Article as art where art.status=1 and art.id!=?0 and art.type.id=?1 order by art.createAt desc";
        return (List<Article>) getSession().createQuery(queryString).setParameter(0, article.getId()).setParameter(1, article.getType().getId()).setFirstResult(0).setMaxResults(5).getResultList();
    }

    @SuppressWarnings("unchecked")
    public Article getPreviousArticle(final Article article) {
        final String queryString = "from Article as art where art.createAt<?0 and art.status=1 order by art.createAt desc";
        Query<Article> query = getSession().createQuery(queryString);
        query.setParameter(0, article.getCreateAt());
        query.setFirstResult(0);
        query.setMaxResults(1);
        try {
            Article previous = (Article) query.getSingleResult();
            return previous;
        } catch (NoResultException e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public Article getNextArticle(final Article article) {
        final String queryString = "from Article as art where art.createAt>?0 and art.status=1 order by art.createAt asc";
        Query<Article> query = getSession().createQuery(queryString);
        query.setParameter(0, article.getCreateAt());
        query.setFirstResult(0);
        query.setMaxResults(1);
        try {
            Article next = (Article) query.getSingleResult();
            return next;
        } catch (NoResultException e) {
            return null;
        }

    }
}
