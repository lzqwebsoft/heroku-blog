package com.herokuapp.lzqwebsoft.service;

import com.herokuapp.lzqwebsoft.dao.LinkDAO;
import com.herokuapp.lzqwebsoft.pojo.Link;
import com.herokuapp.lzqwebsoft.pojo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("linkService")
public class LinkServiceImpl implements LinkService {

    @Autowired
    private LinkDAO linkDAO;

    @Override
    public void save(Link link) {
        // 自动判断是更新还是新建
        if (link.getId() > 0) {
            this.update(link);
        } else {
            link.setId(0);
            Date now = new Date();
            link.setCreateAt(now);
            link.setUpdateAt(now);
            linkDAO.save(link);
        }
    }

    @Override
    public void update(Link link) {
        Link old = linkDAO.getLinkById(link.getId());
        if (old == null)
            return;
        old.setName(link.getName());
        old.setPath(link.getPath());
        old.setRemark(link.getRemark());
        Date now = new Date();
        old.setUpdateAt(now);
        linkDAO.update(old);
    }

    @Override
    public List<Link> getAllLinks() {
        return linkDAO.getAllLinks();
    }

    @Override
    public Page<Link> getAllLinksByPage(int pageNo, int pageSize) {
        return linkDAO.getAllLinksByPage(pageNo, pageSize);
    }

    @Override
    public void delete(int id) {
        if (id <= 0)
            return;
        Link link = linkDAO.getLinkById(id);
        if (link != null)
            linkDAO.delete(link);
    }

    @Override
    public Link getLinkById(int id) {
        return linkDAO.getLinkById(id);
    }
}
