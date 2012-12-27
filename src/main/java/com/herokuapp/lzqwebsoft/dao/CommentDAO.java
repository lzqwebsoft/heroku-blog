package com.herokuapp.lzqwebsoft.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.herokuapp.lzqwebsoft.pojo.Comment;

@Repository("commentDAO")
public class CommentDAO extends BaseDAO {
	
	@SuppressWarnings("unchecked")
	public List<Comment> getParentComment(String articleId) {
		String queryString = "from Comment as com where com.article.id=? and com.parentComment is null order by com.createAt desc";
		return (List<Comment>)getHibernateTemple().find(queryString, new Object[]{articleId});
	}
	
	public void save(Comment comment) {
		getHibernateTemple().save(comment);
	}
	
	public void delete(Comment comment) {
		getHibernateTemple().delete(comment);
	}
	
	public Comment getCommentById(long id) {
		return getHibernateTemple().get(Comment.class, id);
	}
}
