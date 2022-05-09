package com.example.demo.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.mapper.ReplyXMLMapper;
import com.example.demo.vo.ReplyVO;

@Repository
public class ReplyDAO {
	
	@Autowired
	private ReplyXMLMapper m;
	
	public int add(ReplyVO vo) {
        LocalDateTime now = LocalDateTime.now();
        String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd. HH:mm"));
        if(vo.getGrp()!=null) {
        	int grp = Integer.parseInt(vo.getGrp());
        	m.selectMaxgrps(grp);
        	vo.setGrps(m.selectMaxgrps(grp)+1);
        } else {
        	vo.setGrps(0);
        }
        vo.setRepDate(formatedNow);
        m.updateReply(vo.getBno());
		return m.insertAdd(vo);
	}

	public boolean delete(ReplyVO vo) {
		int rno = 0; 
		if(vo.getGrp()==null) {
			rno = vo.getRno();
			if(m.countReply(rno)==0) { 			// 모댓글의 대댓글의 갯수가 0이라면 데이터 삭제
				m.updateCountReply(vo);
				return m.deleteReply(vo) > 0;	
				
			}else {								// 모댓글인데 대댓글이 있다면 cmt="" 변경
				m.updateCountReply(vo);
				return m.comentDelete(vo) > 0;
			}	
		} else {								// 대댓글 삭제
			rno = Integer.parseInt(vo.getGrp());
			if(m.countReply(rno)==1){			// 대댓글이 1개 있는데 모댓글의 cmt=""이라면 모댓글과 대댓글삭제, 아니라면 대댓글만 삭제
				m.deleteReply(vo);
				m.updateCountReply(vo);
				return m.deleteReplyAll(vo) > 0;
					
			}else {								// 대댓글 2개이상이라면 대댓글만 삭제
				m.updateCountReply(vo);
				return m.deleteReply(vo) > 0;
			}	
		} 
	}
}
