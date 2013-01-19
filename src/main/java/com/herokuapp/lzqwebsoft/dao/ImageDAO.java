package com.herokuapp.lzqwebsoft.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.herokuapp.lzqwebsoft.pojo.Image;

@Repository("imageDAO")
public class ImageDAO extends BaseDAO {
    
    public void save(Image image) {
        getHibernateTemple().save(image);
    }
    
    @SuppressWarnings("unchecked")
    public List<Image> getAllImages() {
        return (List<Image>)getHibernateTemple().find("from Image");
    }
    
    public Image getImageById(String id) {
        return getHibernateTemple().get(Image.class, id);
    }
    
    public void delete(Image image) {
        getHibernateTemple().delete(image);
    }
}
