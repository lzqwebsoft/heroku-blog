package com.herokuapp.lzqwebsoft.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.herokuapp.lzqwebsoft.pojo.Comment;

@Repository("commentDAO")
public class CommentDAO extends BaseDAO {

    @SuppressWarnings("unchecked")
    public List<Comment> getParentComment(String articleId) {
        String queryString = "from Comment as com where com.article.id=?0 and com.parentComment is null order by com.createAt desc";
        return (List<Comment>) getSession().createQuery(queryString).setParameter(0, articleId).list();
    }

    public void save(Comment comment) {
        getSession().save(comment);
    }

    public void delete(Comment comment) {
        getSession().delete(comment);
    }

    public Comment getCommentById(long id) {
        return getSession().get(Comment.class, id);
    }
}
