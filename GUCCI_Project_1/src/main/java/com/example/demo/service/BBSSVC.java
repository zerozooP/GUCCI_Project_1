package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.BBSDAO;
import com.example.demo.vo.BBSVO;
import com.example.demo.vo.FileVO;
import com.example.demo.vo.ReplyVO;
import com.github.pagehelper.PageInfo;

@Service
public class BBSSVC {
	
	@Autowired
	private BBSDAO dao;
	
	public FileVO getAttachDetail(Long idx) {
		return dao.getAttachDetail(idx);
	}

	
	public boolean add(BBSVO vo, MultipartFile[] files) {
		return dao.add(vo, files);
	}
	
	public boolean add(BBSVO vo) {
		return dao.add(vo)>0;
	}

	public PageInfo<BBSVO> getList(int pageNum, int pageSize) {
		return dao.getList(pageNum, pageSize);
	}
	
	public List<ReplyVO> reply(int num) {
		return dao.reply(num);
	}
	
	public int incrementCnt(int num) {
		return dao.incrementCnt(num);
		
	}
	public BBSVO detail(int num) {
		return dao.detail(num);
	}
	
	public List<FileVO> getFileList(int num) {
		return dao.getFileList(num);
	}

	public boolean edit(BBSVO vo) {
		return dao.edit(vo)>0;
	}

	public boolean delete(int num) {
		return dao.delete(num)>0;
	}

}