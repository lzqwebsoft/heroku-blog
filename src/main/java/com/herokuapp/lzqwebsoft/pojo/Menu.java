package com.herokuapp.lzqwebsoft.pojo;

import java.util.Date;

/**
 * 定义的一个菜单项实体类
 * @author zqluo
 *
 */
public class Menu {
	private int id;
	private String label;
	private String path;
	private boolean isLogin;
	private Date createAt;
	private Date updateAt;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean getIsLogin() {
		return isLogin;
	}
	public void setIsLogin(boolean isLogin) {
		this.isLogin = isLogin;
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
}
