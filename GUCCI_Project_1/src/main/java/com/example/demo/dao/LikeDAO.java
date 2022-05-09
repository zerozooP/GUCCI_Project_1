package com.example.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.mapper.LikeXMLMapper;
import com.example.demo.vo.LikeVO;

@Repository
public class LikeDAO {
	@Autowired
	private LikeXMLMapper m;

	//  m.updateAddLike(vo)  : bbs 테이블 좋아요 수 1 증가
	//  m.updateDelLike(vo)  : bbs 테이블 좋아요 수 1 감소
	//  m.findLikeUid(vo) 	 : 이미 좋아요를 누른 사람인지 검열, 처음 누른다면 null 이 나옴
	//  m.insertLikeUid(vo)	 : bbs_like 테이블 
	public boolean push(LikeVO vo) {
		String uid = m.findLikeUid(vo);
		System.out.println("uid = " + uid);
		if(uid == null) {
			m.insertLikeUid(vo);
			System.out.println("uid == null 실행중");
			return m.updateAddLike(vo) > 0;
		} else {
			System.out.println("uid != null 실행중");
			m.deleteLikeUid(vo);
			return m.updateDelLike(vo) > 0;
		}
	}
}
