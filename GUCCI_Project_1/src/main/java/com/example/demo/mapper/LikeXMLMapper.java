package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.LikeVO;

@Mapper
public interface LikeXMLMapper {
	
	int updateAddLike(LikeVO vo);

	int updateDelLike(LikeVO vo);

	String findLikeUid(LikeVO vo);

	int insertLikeUid(LikeVO vo);

	int deleteLikeUid(LikeVO vo);

}	