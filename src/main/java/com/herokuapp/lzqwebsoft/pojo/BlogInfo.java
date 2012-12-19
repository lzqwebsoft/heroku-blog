package com.herokuapp.lzqwebsoft.pojo;

import java.util.Date;

/**
 * 封装博客信息类
 * @author zqluo
 *
 */
public class BlogInfo {
	private int id;
	private String head;
	private String descriptions;
	private String about;
	private String email;
	private long accessNum;
	private Date updateAt;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getAccessNum() {
		return accessNum;
	}
	public void setAccessNum(long accessNum) {
		this.accessNum = accessNum;
	}
	public Date getUpdateAt() {
		return updateAt;
	}
	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}
}
