package com.herokuapp.lzqwebsoft.service;

import com.herokuapp.lzqwebsoft.pojo.Link;
import com.herokuapp.lzqwebsoft.pojo.Page;

import java.util.List;

public interface LinkService {
    /**
     * 保存友链对象
     *
     * @param link 友链对象
     */
    void save(Link link);

    /**
     * 链接对象更新
     *
     * @param link 链接对象
     */
    void update(Link link);

    /**
     * 返回系统中保存的所有友情链接
     *
     * @return 链接Link List集合
     */
    List<Link> getAllLinks();

    /**
     * 返回系统中保存的所有上传的图片
     *
     * @param pageNo   当前的页数
     * @param pageSize 每页显示数
     * @return 图片Image List集合
     */
    Page<Link> getAllLinksByPage(int pageNo, int pageSize);

    /**
     * 根据友链对象的ID来删除指定的记录
     *
     * @param id 友链id
     */
    void delete(int id);

    /**
     * 根据友链id来得到指定的友链对象
     *
     * @param id 友链id
     * @return 友链对象
     */
    Link getLinkById(int id);
}