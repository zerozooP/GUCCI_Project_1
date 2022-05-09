package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.ReplyVO;

@Mapper
public interface ReplyXMLMapper {

	int insertAdd(ReplyVO vo);
	int deleteReply(ReplyVO vo);
	int updateReply(int bno);
	int selectMaxgrps(int grp);
	int countReply(int rno);
	int comentDelete(ReplyVO vo);
	int updateCountReply(ReplyVO vo);
	int deleteReplyAll(ReplyVO vo);
	String selectCmt(ReplyVO vo);
}	