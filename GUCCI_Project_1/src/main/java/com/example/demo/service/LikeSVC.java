package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.LikeDAO;
import com.example.demo.vo.LikeVO;

@Service
public class LikeSVC {
	
	@Autowired
	private LikeDAO dao;

	public boolean push(LikeVO vo) {
		return dao.push(vo);
	}
}
