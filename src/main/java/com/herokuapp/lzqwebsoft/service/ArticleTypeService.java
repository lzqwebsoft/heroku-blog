package com.herokuapp.lzqwebsoft.service;

import java.util.List;

import com.herokuapp.lzqwebsoft.pojo.ArticleType;

public interface ArticleTypeService {
	
	/**
	 * 得到系统中所有的文章类型
	 * @return 所有ArticleType的List集合
	 */
	public List<ArticleType> getAllArticleType();
	
	/**
	 * 将一个文章类型对象入库
	 * @param type 文章对象
	 * @return 保存数据库的文章对象
	 */
	public ArticleType save(ArticleType type);
	
	/**
	 * 根据ID来删除类型对象
	 * @param id 类型对象ID
	 */
	public void delete(String id);
	
	/**
	 * 显示或隐藏文章类型
	 * @param id 类型对象ID
	 * @param disable 是否隐藏
	 */
	public void disable(String id, boolean disable);
	
	/**
	 * 更新文章类型的名称
	 * @param id 类型对象ID
	 * @param type_name 新名称
	 */
	public void update(String id, String type_name);
	
	/**
	 * 由类型的id来得到一个类型对象
	 * @param id 类型id
	 * @return
	 */
	public ArticleType get(int id);
}
