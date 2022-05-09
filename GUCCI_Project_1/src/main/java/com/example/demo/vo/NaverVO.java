package com.example.demo.vo;

public class NaverVO {
	private String uid;
	private String email;
	private String naverid;
	
	public NaverVO() {}
	public NaverVO(String uid, String email, String naverid) {
		super();
		this.uid = uid;
		this.email = email;
		this.naverid = naverid;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNaverid() {
		return naverid;
	}
	public void setNaverid(String id) {
		this.naverid = id;
	}
}
