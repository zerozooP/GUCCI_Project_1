package com.example.demo.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.mapper.BBSXMLMapper;
import com.example.demo.mapper.FileXMLMapper;
import com.example.demo.util.FileUtils;
import com.example.demo.vo.BBSVO;
import com.example.demo.vo.FileVO;
import com.example.demo.vo.ReplyVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Repository
public class BBSDAO {
	
	private HttpSession session;

	@Autowired
	private BBSXMLMapper m;
	
	@Autowired
	public BBSDAO(HttpSession session) {
		this.session = session;
	}
	
	@Autowired
	private FileUtils fileUtils;
	
	@Autowired
	private FileXMLMapper fileMapper;

	
	public boolean add(BBSVO vo, MultipartFile[] files) {
		int queryResult = 0;
		
		if (vo.getNum() == 0) {
			int bno = fileMapper.readLastBno();
			vo.setNum(bno+1);
		}
		
		List<FileVO> fileList = fileUtils.uploadFiles(files, vo.getNum());
		if (CollectionUtils.isEmpty(fileList) == false) {
			queryResult = fileMapper.insertAttach(fileList);
			if (queryResult < 1) {
				queryResult = 0;
			}
		}

		return (queryResult > 0);
	}
	
	public int add(BBSVO vo) {
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        Date dateObj = calendar.getTime();
        
        String formattedDate = dtf.format(dateObj);
		String uid = (String)session.getAttribute("uid");
		String email = (String)session.getAttribute("email");
		
		vo.setUid(uid);
		vo.setEmail(email);
		vo.setDate(formattedDate);
		
		return m.insertAdd(vo);
	}
	
	public List<ReplyVO> reply(int num) {
		return m.selectReply(num);
	}

	public PageInfo<BBSVO> getList(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
	    PageInfo<BBSVO> pageInfo = new PageInfo<>(m.getList());
	    return pageInfo;
	}
	
	public int incrementCnt(int num) {
		return m.updateCnt(num);
	}

	public BBSVO detail(int num) {
		return m.detail(num);
	}

	public int edit(BBSVO vo) {
		
		// 파일이 추가, 삭제, 변경된 경우
		if("Y".equals(vo.getChangeYn())) {
			fileMapper.deleteAttach(vo.getNum());
			
			// fileIdxs에 포함된 num을 가지는 파일의 삭제여부를 'N'으로 업데이트
			if (CollectionUtils.isEmpty(vo.getFileIdxs()) == false) {
				fileMapper.undeleteAttach(vo.getFileIdxs());
			}
		}
		
		return m.edit(vo);
	}

	public int delete(int num) {
		return m.delete(num);
	}

	public List<FileVO> getFileList(int num) {
		int fileTotalCount = fileMapper.selectAttachTotalCount(num);
		System.out.println("fileTotalCount :" + fileTotalCount);
		if (fileTotalCount < 1) {
			System.out.println("fileTotal < 0");
			return Collections.emptyList();
		}
		return fileMapper.selectAttachList(num);
	}

	public FileVO getAttachDetail(Long idx) {
		return fileMapper.selectAttachDetail(idx);
	}

}