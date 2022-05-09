package com.example.demo.vo;

import org.springframework.stereotype.Component;

@Component
public class LikeVO {
	
	private int lno;
	private int bno;
	private String like_uid;
	
	public LikeVO() {}

	public LikeVO(int lno, int bno, String like_uid) {
		super();
		this.lno = lno;
		this.bno = bno;
		this.like_uid = like_uid;
	}

	public int getLno() {
		return lno;
	}

	public void setLno(int lno) {
		this.lno = lno;
	}

	public int getBno() {
		return bno;
	}

	public void setBno(int bno) {
		this.bno = bno;
	}

	public String getLike_uid() {
		return like_uid;
	}

	public void setLike_uid(String like_uid) {
		this.like_uid = like_uid;
	}
}
