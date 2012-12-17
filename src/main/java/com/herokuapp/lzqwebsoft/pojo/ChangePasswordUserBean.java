package com.herokuapp.lzqwebsoft.pojo;

/**
 * 封装用户更改密码的Form对象
 * @author zqluo
 *
 */
public class ChangePasswordUserBean {
	private String password;
	private String newPassword;
	private String confirmPassword;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
