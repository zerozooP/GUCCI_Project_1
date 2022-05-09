package com.example.demo.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.demo.vo.KakaoVO;
import com.example.demo.vo.UserVO;
import com.example.demo.dao.UserDAO;
import com.example.demo.mapper.UserXMLMapper;


@Service
public class UserSVC {
	
	@Autowired
	private UserXMLMapper UM;
	
	private HttpSession session;
	
	@Autowired
	public UserSVC(HttpSession session) {
		this.session = session;
	}
	
	@Autowired
	private UserDAO dao;
	
   public UserVO readLogin(UserVO vo) {
	      return dao.readLogin(vo);
   }
	
	public boolean insertUser(UserVO user) {
		boolean ok = (dao.insertUser(user))>0;
		return ok;
	}
	
	public boolean login(UserVO user) {
		UserVO u = dao.login(user);
		if(u!=null) {
			session.setAttribute(u.getUid(),u.getPwd());
			return true;
		}else {
			return false;
		}
	}
	
	public void kakaoLogin(KakaoVO user) {
		
    	session.setAttribute(user.getUid(),user.getEmail());
		   
	}
	
	public boolean isLogin() {
		if(session.getAttribute("uid")!=null) {
			return true;
		}
		return false;
	}
	
	public boolean updateUser(UserVO user) {
		String uid = (String) session.getAttribute("uid");
		user.setUid(uid);
		boolean up = (dao.updateUser(user))>0;
		return up;
	}
	
	public boolean lostPw(UserVO user, String ran2) {
		
		
		boolean bb = (dao.lostPw(user,ran2))>0;
		return bb;
	}
}
