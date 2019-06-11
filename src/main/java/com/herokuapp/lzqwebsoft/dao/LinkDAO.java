package com.herokuapp.lzqwebsoft.dao;

import com.herokuapp.lzqwebsoft.pojo.Link;
import com.herokuapp.lzqwebsoft.pojo.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("linkDAO")
public class LinkDAO extends PageBaseDAO<Link> {

    public void save(Link link) {
        getSession().save(link);
    }

    public void update(Link link) {
        getSession().update(link);
    }

    @SuppressWarnings("unchecked")
    public List<Link> getAllLinks() {
        return (List<Link>) getSession().createQuery("FROM Link").getResultList();
    }

    public Page<Link> getAllLinksByPage(int pageNo, int pageSize) {
        String queryString = "FROM Link AS ink ORDER BY ink.id ASC";
        return pagedQuery(queryString, pageNo, pageSize);
    }

    public Link getLinkById(int id) {
        return getSession().get(Link.class, id);
    }

    public void delete(Link link) {
        getSession().delete(link);
    }
}
