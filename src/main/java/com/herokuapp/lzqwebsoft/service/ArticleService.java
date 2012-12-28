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
	 * 得到系统中所有的文章，带内容
	 * @return 文章List集合
	 */
	public List<Article> getAllAricle();
	
	/**
	 * 得到系统中的所有文章，不带内容
	 * @return 文章List集合
	 */
	public List<Article> getAllAricleWithoutContent();
	
	/**
	 * 得到系统中的所有文章草稿
	 * @return 文章草稿List集合
	 */
	public List<Article> getAllDrafts();
	
	/**
	 * 根据文章的类型id与标题title来检索数据库，得到相应的文章集合
	 * @param articleTypeId 文章类型id
	 * @param title 标题title
	 * @return 文章Article对象List集合
	 */
	public List<Article> getArticleByTypeAndTitle(int articleTypeId, String title);
	
	/**
	 * 更新文章的是否允许评论属性
	 * @param articleId 文章ID
	 * @param allowComment 是否允许属性值
	 */
	public void updateAllowComment(String articleId, boolean allowComment);
	
	/**
	 * 设置文章的置顶
	 * @param articleId 文章ID
	 * @param isTop 是否置顶
	 */
	public void updateIsTop(String articleId, boolean isTop);
	
	/**
	 * 查询得到指定类型的文章
	 * @param typeId 文章类型ID
	 */
	public List<Article> getArticleByTypeId(int typeId);
	
	/**
	 * 得到阅读人次前10的文章排行榜
	 * @return 文章Article对象List集合
	 */
	public List<Article> getReadedTop10();
}
