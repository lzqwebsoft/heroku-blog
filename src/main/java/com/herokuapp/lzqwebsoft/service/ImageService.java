package com.herokuapp.lzqwebsoft.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.herokuapp.lzqwebsoft.pojo.Image;
import com.herokuapp.lzqwebsoft.pojo.Page;

public interface ImageService {

    /**
     * 实现图片的上传与入库，返回上传后图片在服务器端的路径
     * 
     * @param file
     *            MultipartFile对象，用于封装用户上传的文件流对象
     * @param image
     *            图片的描述信息对象
     * @return 图片在服务器端的路径
     */
    public String save(MultipartFile file, Image image);

    /**
     * 图片信息更新
     * 
     * @param image
     *            图片的描述信息对象
     */
    public void update(Image image);

    /**
     * 返回系统中保存的所有上传的图片
     * 
     * @return 图片Image List集合
     */
    public List<Image> getAllImages();

    /**
     * 返回系统中保存的所有上传的图片
     * 
     * @param pageNo
     *            当前的页数
     * @param pageSize
     *            每页显示数
     * @return 图片Image List集合
     */
    public Page<Image> getAllImagesByPage(int pageNo, int pageSize);

    /**
     * 根据图片对象的ID来删除指定的记录
     * 
     * @param id
     *            图片ID
     */
    public void delete(String id);

    /**
     * 根据图片的id来得到指定的图片对象
     * 
     * @param id
     *            图片id
     * @return 图片对象
     */
    public Image getImageById(String id);
}
