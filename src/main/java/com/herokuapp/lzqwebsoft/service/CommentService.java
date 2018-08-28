package com.herokuapp.lzqwebsoft.service;

import java.util.List;

import com.herokuapp.lzqwebsoft.pojo.Comment;

public interface CommentService {
	
	/**
	 * 根据文章的ID得到所有的一级评论
	 * @param articleId 文章id
	 * @return 评论的List集合
	 */
    List<Comment> getAllParentComment(String articleId);
	
	/**
	 * 保存指定的评论Comment对象
	 * @param comment 评论对象
	 */
    void save(Comment comment);
	
	/**
	 * 根据ID来删除指定的评论
	 * @param id 评论的Id
	 * @return 关联的博文Article对象id
	 */
    String delete(long id);
}
