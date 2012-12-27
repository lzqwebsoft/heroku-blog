package com.herokuapp.lzqwebsoft.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.herokuapp.lzqwebsoft.dao.ArticleDAO;
import com.herokuapp.lzqwebsoft.dao.CommentDAO;
import com.herokuapp.lzqwebsoft.pojo.Article;
import com.herokuapp.lzqwebsoft.pojo.Comment;

@Service("commentService")
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentDAO commentDAO;
	@Autowired
	private ArticleDAO articleDAO;

	@Override
	public List<Comment> getAllParentComment(String articleId) {
		return commentDAO.getParentComment(articleId);
	}

	@Override
	public void save(Comment comment) {
		Article article = articleDAO.getArticleById(comment.getArticle().getId());
		comment.setArticle(article);
		comment.setCreateAt(new Date());
		if(comment.getParentComment()!=null&&comment.getParentComment().getId()!=0) {
			Comment parentComment = commentDAO.getCommentById(comment.getParentComment().getId());
			comment.setParentComment(parentComment);
		}
		commentDAO.save(comment);
	}

	@Override
	public String delete(long id) {
		Comment comment = commentDAO.getCommentById(id);
		if(comment!=null) {
			String articleId = comment.getArticle().getId();
			commentDAO.delete(comment);
			return articleId;
		}
		return null;
	}
}
