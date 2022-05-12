package com.example.demo.vo;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class BBSVO {
	
	private int Num;
	private	String uid;
	private String email;
	private String title;
	private String content;
	private String date;
	private int cnt;
	private int reply;
	private int liked;
	private String changeYn;
	private String deleteYn;
	private List<Long> fileIdxs;
	private String category;
	
	
	public BBSVO() {}

	public BBSVO(int num, String uid, String email, String title, String content, 
			String date, int reply, int liked, String category) {
		super();
		Num = num;
		this.uid = uid;
		this.email = email;
		this.title = title;
		this.content = content;
		this.date = date;
		this.reply = reply;
		this.liked = liked;
		this.category = category;
	}
	
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDeleteYn() {
		return deleteYn;
	}

	public void setDeleteYn(String deleteYn) {
		this.deleteYn = deleteYn;
	}

	public String getChangeYn() {
		return changeYn;
	}

	public void setChangeYn(String changeYn) {
		this.changeYn = changeYn;
	}

	public List<Long> getFileIdxs() {
		return fileIdxs;
	}

	public void setFileIdxs(List<Long> fileIdxs) {
		this.fileIdxs = fileIdxs;
	}

	
	public int getLiked() {
		return liked;
	}

	public void setLiked(int liked) {
		this.liked = liked;
	}
	
	public int getReply() {
		return reply;
	}

	public void setReply(int reply) {
		this.reply = reply;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public int getNum() {
		return Num;
	}

	public void setNum(int num) {
		Num = num;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}