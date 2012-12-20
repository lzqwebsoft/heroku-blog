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
}
