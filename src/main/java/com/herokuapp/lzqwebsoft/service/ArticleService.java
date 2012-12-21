package com.herokuapp.lzqwebsoft.service;

import com.herokuapp.lzqwebsoft.pojo.Article;

public interface ArticleService {
	
	/**
	 * 保存指定的文章对象
	 * @param typeName 文章类型名
	 * @param article 文章对象
	 * @param modelNew 文章关联类型的模式，是否新建一个名为typeName的类型
	 */
	public void save(Article article, String typeName, boolean modelNew);
	
	/**
	 * 由指定的ID得到文章对象
	 * @param id 文章ID
	 * @return 文章对象
	 */
	public Article get(String id);
}
