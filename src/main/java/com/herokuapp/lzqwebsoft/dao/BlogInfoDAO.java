package com.herokuapp.lzqwebsoft.dao;

import org.springframework.stereotype.Repository;

import com.herokuapp.lzqwebsoft.pojo.BlogInfo;

@Repository("blogInfoDAO")
public class BlogInfoDAO extends BaseDAO {

    public void upate(BlogInfo blogInfo) {
        getSession().update(blogInfo);
    }

    public void save(BlogInfo blogInfo) {
        getSession().save(blogInfo);
    }

    public BlogInfo findById(int blogInfoId) {
        return getSession().get(BlogInfo.class, blogInfoId);
    }

    public BlogInfo getBlogInfo() {
        String queryString = "from BlogInfo binfo order by binfo.updateAt desc";
        BlogInfo blogInfo = (BlogInfo) getSession().createQuery(queryString).setFirstResult(0).setMaxResults(1).getSingleResult();
        return blogInfo;
    }
}
