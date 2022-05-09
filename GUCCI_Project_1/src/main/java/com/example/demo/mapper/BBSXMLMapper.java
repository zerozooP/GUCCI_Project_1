package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.BBSVO;
import com.example.demo.vo.ReplyVO;

@Mapper
public interface BBSXMLMapper {

	int insertAdd(BBSVO vo);
	List<BBSVO> getList();
	BBSVO detail(int num);
	int edit(BBSVO vo);
	int delete(int num);
	List<ReplyVO> selectReply(int num);
	int updateCnt(int num);
}