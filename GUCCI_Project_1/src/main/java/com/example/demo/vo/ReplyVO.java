package com.example.demo.vo;

import org.springframework.stereotype.Component;

@Component
public class ReplyVO {
	
	private int rno;		// 댓글 고유 번호
	private int bno;		// 해당 댓글의 게시글 번호
	private String grp;		// 댓글이 속한 댓글 번호 // null 이면 모댓글
	private int grps;		// 같은 grp중에 순서
	private String uid;		// 댓글 작성자
	private String cmt;		// 댓글 내용
	private String repDate;	// 댓글 작성일
	
	public ReplyVO() {}

	public ReplyVO(int rno, int bno, String grp, int grps, String uid, String cmt, String repDate) {
		super();
		this.rno = rno;
		this.bno = bno;
		this.grp = grp;
		this.grps = grps;
		this.uid = uid;
		this.cmt = cmt;
		this.repDate = repDate;
	}

	public int getRno() {
		return rno;
	}

	public void setRno(int rno) {
		this.rno = rno;
	}

	public int getBno() {
		return bno;
	}

	public void setBno(int bno) {
		this.bno = bno;
	}

	public String getGrp() {
		return grp;
	}

	public void setGrp(String grp) {
		this.grp = grp;
	}

	public int getGrps() {
		return grps;
	}

	public void setGrps(int grps) {
		this.grps = grps;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getCmt() {
		return cmt;
	}

	public void setCmt(String cmt) {
		this.cmt = cmt;
	}

	public String getRepDate() {
		return repDate;
	}

	public void setRepDate(String repDate) {
		this.repDate = repDate;
	}
}
