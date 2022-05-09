package com.example.demo.vo;

public class KakaoVO {
	private String uid;
	private String email;
	private int kakaoid;
	
	public KakaoVO() {}
	
	public KakaoVO(String uid, String email,int kakaoid) {
		super();
		this.uid = uid;
		this.email = email;
		this.kakaoid = kakaoid;
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

	public int getKakaoid() {
		return kakaoid;
	}

	public void setKakaoid(int kakaoid) {
		this.kakaoid = kakaoid;
	}
	
	
}
