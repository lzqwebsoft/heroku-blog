package com.herokuapp.lzqwebsoft.pojo;

import java.util.Date;
import java.util.Set;

/**
 * 封装文章对象
 * @author zqluo
 *
 */
public class Article {
	private String id;
	private User author;
	private ArticleType type;    // 关联一个文章类型
	private int patternTypeId;
	private String title;
	private boolean allowComment;
	private boolean isTop;
	private String content;
	private int status;
	private long readedNum;
	private Date createAt;
	private Date updateAt;
	private Set<Comment> comments;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public ArticleType getType() {
		return type;
	}
	public void setType(ArticleType type) {
		this.type = type;
	}
	public int getPatternTypeId() {
		return patternTypeId;
	}
	public void setPatternTypeId(int patternTypeId) {
		this.patternTypeId = patternTypeId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean getAllowComment() {
		return allowComment;
	}
	public void setAllowComment(boolean allowComment) {
		this.allowComment = allowComment;
	}
	public boolean getIsTop() {
		return isTop;
	}
	public void setIsTop(boolean isTop) {
		this.isTop = isTop;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public long getReadedNum() {
		return readedNum;
	}
	public void setReadedNum(long readedNum) {
		this.readedNum = readedNum;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	public Date getUpdateAt() {
		return updateAt;
	}
	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}
	public Set<Comment> getComments() {
		return comments;
	}
	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}
}
