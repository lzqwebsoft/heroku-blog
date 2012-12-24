package com.herokuapp.lzqwebsoft.service;

import java.util.List;

import com.herokuapp.lzqwebsoft.pojo.Article;

public interface ArticleService {
	
	/**
	 * 保存指定的文章对象
	 * @param typeName 文章类型名
	 * @param article 文章对象
	 * @param modelNew 文章关联类型的模式，是否新建一个名为typeName的类型
	 * @param isDraft 保存文章为草稿
	 */
	public void save(Article article, String typeName, boolean modelNew, boolean isDraft);
	
	/**
	 * 更新指定的文章对象
	 * @param article 文章对象
	 * @param typeName 文章类型名
	 * @param modelNew 文章关联类型的模式，是否新建一个名为typeName的类型
	 * @param isDraft 保存文章为草稿
	 */
	public void update(Article article, String typeName, boolean modelNew, boolean isDraft);
	
	/**
	 * 由指定的ID得到文章对象
	 * @param id 文章ID
	 * @return 文章对象
	 */
	public Article get(String id);
	
	/**
	 * 根据指定的ID删除文章
	 * @param id 文章ID
	 */
	public void delete(String id);
	
	/**
	 * 得到系统中所有的文章
	 * @return 文章List集合
	 */
	public List<Article> getAllAricle();
}
