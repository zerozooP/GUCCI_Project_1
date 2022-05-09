package com.example.demo.vo;

public class UserVO {
	private int num;
	private String uid;
	private String pwd;
	private String phone;
	private String address;
	private String name;
	private String email;
	
	public UserVO() {}
	
	public UserVO(int num, String uid, String pwd, String phone, String address, String name, String email) {
		super();
		this.num = num;
		this.uid = uid;
		this.pwd = pwd;
		this.phone = phone;
		this.address = address;
		this.name = name;
		this.email = email;
	}
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
