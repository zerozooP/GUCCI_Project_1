package com.example.demo.vo;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component
public class FileVO {

	/* 파일 번호 (PK) */
	private Long idx;

	/* 게시글 번호 (FK) */
	private int bno;

	/* 원본 파일명 */
	private String originalName;

	/* 저장 파일명 */
	private String saveName;

	/* 파일 크기 */
	private long size;

	/** 삭제 여부 */
	private String deleteYn;

	/** 등록일 */
	private LocalDateTime insertTime;

	/** 삭제일 */
	private LocalDateTime deleteTime;

	public FileVO() {}

	public FileVO(Long idx, int bno, String originalName, String saveName, long size, String deleteYn,
			LocalDateTime insertTime, LocalDateTime deleteTime) {
		super();
		this.idx = idx;
		this.bno = bno;
		this.originalName = originalName;
		this.saveName = saveName;
		this.size = size;
		this.deleteYn = deleteYn;
		this.insertTime = insertTime;
		this.deleteTime = deleteTime;
	}

	public Long getIdx() {
		return idx;
	}

	public void setIdx(Long idx) {
		this.idx = idx;
	}

	public int getBno() {
		return bno;
	}

	public void setBno(int bno) {
		this.bno = bno;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getSaveName() {
		return saveName;
	}

	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getDeleteYn() {
		return deleteYn;
	}

	public void setDeleteYn(String deleteYn) {
		this.deleteYn = deleteYn;
	}

	public LocalDateTime getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(LocalDateTime insertTime) {
		this.insertTime = insertTime;
	}

	public LocalDateTime getDeleteTime() {
		return deleteTime;
	}

	public void setDeleteTime(LocalDateTime deleteTime) {
		this.deleteTime = deleteTime;
	}
}