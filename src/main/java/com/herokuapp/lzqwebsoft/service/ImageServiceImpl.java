package com.herokuapp.lzqwebsoft.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.herokuapp.lzqwebsoft.dao.ImageDAO;
import com.herokuapp.lzqwebsoft.pojo.Image;
import com.herokuapp.lzqwebsoft.util.CommonConstant;
import com.herokuapp.lzqwebsoft.util.SHA1Util;

@Service("imageService")
public class ImageServiceImpl implements ImageService {
    
    @Autowired
    private ImageDAO imageDAO;

    @Override
    public String save(MultipartFile file, Image image) {
        // 使用SHA1Util产生一个随机的16位的图片文件名
        String originalFilename = file.getOriginalFilename();
        int index = originalFilename.lastIndexOf('.');
        StringBuffer filePath = new StringBuffer(SHA1Util.generateSalt())
                .append(originalFilename.subSequence(index, originalFilename.length()));
        
        image.setFileName(originalFilename);
        image.setCreateAt(new Date());
        image.setDiskFilename(filePath.toString());
        image.setSize(file.getSize());
        
        File imageFile  = new File(CommonConstant.IMAGE_DIR.toString()+"/"+filePath);
        try {
            if(!imageFile.exists())
                imageFile.createNewFile();
            // 将文件流保存到指定的文件
            file.transferTo(imageFile);
            // 图片入库
            imageDAO.save(image);
            
            return image.getDiskFilename();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Image> getAllImages() {
        return imageDAO.getAllImages();
    }
}
