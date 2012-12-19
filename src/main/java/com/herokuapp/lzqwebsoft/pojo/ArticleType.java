package com.herokuapp.lzqwebsoft.pojo;

import java.util.Date;
import java.util.Set;

/**
 * 封装文章类型
 * @author zqluo
 *
 */
public class ArticleType {
	private int id;
	private String name;
	private Date createAt;
	private Date updateAt;
	private Set<Article> articles;  // 关联多篇文章
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Set<Article> getArticles() {
		return articles;
	}
	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}
}
