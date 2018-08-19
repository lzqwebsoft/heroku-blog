package com.herokuapp.lzqwebsoft.dao;

import java.util.List;

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

    @SuppressWarnings("unchecked")
    public BlogInfo getBlogInfo() {
        String queryString = "from BlogInfo binfo order by binfo.updateAt desc";
        List<BlogInfo> list = getSession().createQuery(queryString).setFirstResult(0).setMaxResults(1).list();
        BlogInfo blogInfo = null;
        if (list != null && list.size() > 0)
            blogInfo = list.get(0);
        return blogInfo;
    }
}
