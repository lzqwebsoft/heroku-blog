package com.herokuapp.lzqwebsoft.pojo;

import java.util.Date;
import java.util.Set;

/**
 * 封装文章的评论对象
 *
 * @author zqluo
 */
public class Comment {
    private long id;
    private Article article;              // 关联的一篇文章
    private String reviewer;
    private String website;
    private String content;
    private String fromIP;                // 来源IP
    private String fromLocal;             // 来源地区
    private boolean isBlogger;            // 标记此条评论是否由博主产生
    private Comment parentComment;        // 对应的父评论
    private Comment rootComment;          // 对应的顶级评论
    private Set<Comment> childComments;   // 对应的子评论
    private Set<Comment> allChildComments;       // 对应的所有子评论
    private Date createAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getContent() {
        return content;
    }

    public String getFromIP() {
        return fromIP;
    }

    public void setFromIP(String fromIP) {
        this.fromIP = fromIP;
    }

    public String getFromLocal() {
        return fromLocal;
    }

    public String getFromLocalLabel() {
        if (fromLocal != null && fromLocal.trim().length() > 0 && !fromLocal.equalsIgnoreCase("保留地址")) {
            return "(" + fromLocal + ")";
        } else {
            return "";
        }
    }

    public void setFromLocal(String fromLocal) {
        this.fromLocal = fromLocal;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean getIsBlogger() {
        return isBlogger;
    }

    public void setIsBlogger(boolean isBlogger) {
        this.isBlogger = isBlogger;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public Comment getRootComment() {
        return rootComment;
    }

    public void setRootComment(Comment rootComment) {
        this.rootComment = rootComment;
    }

    public Set<Comment> getChildComments() {
        return childComments;
    }

    public void setChildComments(Set<Comment> childComments) {
        this.childComments = childComments;
    }

    public Set<Comment> getAllChildComments() {
        return allChildComments;
    }

    public void setAllChildComments(Set<Comment> allChildComments) {
        this.allChildComments = allChildComments;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
