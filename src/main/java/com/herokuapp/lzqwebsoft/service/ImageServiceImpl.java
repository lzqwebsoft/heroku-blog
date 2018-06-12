package com.herokuapp.lzqwebsoft.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.herokuapp.lzqwebsoft.dao.ImageDAO;
import com.herokuapp.lzqwebsoft.pojo.Image;
import com.herokuapp.lzqwebsoft.util.QiniuUtil;
import com.herokuapp.lzqwebsoft.util.SHA1Util;

@Service("imageService")
public class ImageServiceImpl implements ImageService {
    
    @Autowired
    private ImageDAO imageDAO;

    @Override
    public String save(MultipartFile file, Image image) {
        String originalFilename = file.getOriginalFilename();
        // 使用SHA1Util产生一个随机的16位与时间截组成上传图片id
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
        StringBuffer id = new StringBuffer(format.format(new Date()))
                .append(SHA1Util.generateSalt());
        image.setId(id.toString());
        image.setFileName(originalFilename);
        image.setCreateAt(new Date());
        image.setSize(file.getSize());
        try {
            image.setContent(file.getBytes());
            // 添加七牛云文件备份
            image = QiniuUtil.upload(image);
            imageDAO.save(image);
            return "images/show/"+ image.getId() +".html";
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return "resources/images/NoImageShow.jpg";
    }

    @Override
    public void update(Image image) {
        imageDAO.update(image);
    }

    @Override
    public List<Image> getAllImages() {
        return imageDAO.getAllImages();
    }

    @Override
    public void delete(String id) {
        Image image = imageDAO.getImageById(id);
        if(image!=null) {
            // 添加七牛云删除文件
            QiniuUtil.delete(image);
            imageDAO.delete(image);
        }
    }

    @Override
    public Image getImageById(String id) {
        return imageDAO.getImageById(id);
    }
}
