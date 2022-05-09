package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.FileVO;

@Mapper
public interface FileXMLMapper {
	
	public int insertAttach(List<FileVO> attachList);

	public FileVO selectAttachDetail(long idx);

	public int deleteAttach(int idx);

	public List<FileVO> selectAttachList(int idx);

	public int selectAttachTotalCount(int idx);

	public int readLastBno();
	
	public int undeleteAttach(List<Long> list);
}