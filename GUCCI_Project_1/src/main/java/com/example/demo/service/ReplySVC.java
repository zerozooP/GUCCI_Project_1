package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ReplyDAO;
import com.example.demo.vo.ReplyVO;

@Service
public class ReplySVC {
	
	@Autowired
	private ReplyDAO dao;

	public boolean add(ReplyVO vo) {
		return dao.add(vo)>0;
	}

	public boolean delete(ReplyVO vo) {
		return dao.delete(vo);
	}
}
