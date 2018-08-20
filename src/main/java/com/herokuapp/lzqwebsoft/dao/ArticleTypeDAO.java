package com.herokuapp.lzqwebsoft.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.herokuapp.lzqwebsoft.pojo.ArticleType;

@Repository("articleTypeDAO")
public class ArticleTypeDAO extends BaseDAO {

    public ArticleType save(ArticleType articleType) {
        getSession().save(articleType);
        return articleType;
    }

    public void update(ArticleType articleType) {
        getSession().update(articleType);
    }

    public void delete(ArticleType articleType) {
        getSession().delete(articleType);
    }

    public ArticleType getArticleTypeById(int id) {
        return getSession().get(ArticleType.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<ArticleType> getAllArticleType() {
        String queryString = "from ArticleType";
        return getSession().createQuery(queryString).list();
    }

    public ArticleType getArticleTypeByName(String name) {
        String queryString = "from ArticleType art where art.name=?0";
        try {
            ArticleType articleType = (ArticleType)getSession().createQuery(queryString).setParameter(0, name).getSingleResult();
            return articleType;
        } catch(NoResultException e) {
            return null;
        }
        
    }
}
