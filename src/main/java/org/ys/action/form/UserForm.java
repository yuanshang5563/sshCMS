package org.ys.action.form;

import java.sql.Date;

import org.apache.struts.action.ActionForm;

public class UserForm extends ActionForm {
	private static final long serialVersionUID = -4615743519282006977L;
	private String userId;
	private String userName;
	private String password;
	private String address;
	private String email;
	private int age;
	private Date birthday;
	private String commentContext;
	private String sex;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getCommentContext() {
		return commentContext;
	}
	public void setCommentContext(String commentContext) {
		this.commentContext = commentContext;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
}
