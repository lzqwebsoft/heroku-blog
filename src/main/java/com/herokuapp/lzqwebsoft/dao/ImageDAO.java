package com.herokuapp.lzqwebsoft.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.herokuapp.lzqwebsoft.pojo.Image;
import com.herokuapp.lzqwebsoft.pojo.Page;

@Repository("imageDAO")
public class ImageDAO extends PageBaseDAO<Image> {

    public void save(Image image) {
        getHibernateTemple().save(image);
    }

    public void update(Image image) {
        getHibernateTemple().update(image);
    }

    @SuppressWarnings("unchecked")
    public List<Image> getAllImages() {
        return (List<Image>) getHibernateTemple().find("FROM Image");
    }
    
    public Page<Image> getAllImagesByPage(int pageNo, int pageSize) {
        String queryString = "FROM Image AS img ORDER BY img.id ASC";
        return pagedQuery(queryString, pageNo, pageSize);
    }

    public Image getImageById(String id) {
        return getHibernateTemple().get(Image.class, id);
    }

    public void delete(Image image) {
        getHibernateTemple().delete(image);
    }
}
