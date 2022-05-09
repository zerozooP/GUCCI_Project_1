package com.example.demo.dao;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Repository;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.mapper.UserXMLMapper;
import com.example.demo.vo.KakaoVO;
import com.example.demo.vo.NaverVO;
import com.example.demo.vo.UserVO;

@Repository
public class UserDAO {
	
	@Autowired
	private UserXMLMapper UM;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
   public UserVO readLogin(UserVO vo) {
      return UM.selectLogin(vo);
   }
	
	public int insertUser(UserVO user) {
		String encodePassword = passwordEncoder.encode(user.getPwd());
        user.setPwd(encodePassword);
		return UM.insertUser(user);
	}
	
	public int kakaoUser(KakaoVO kakao) {
		return UM.kakaoUser(kakao);
	}
	
	public int naverUser(NaverVO naver) {
		return UM.naverUser(naver);
	}
	
	public UserVO login(UserVO user) {
		 UserVO U = UM.checkById(user.getUid());
		 boolean check = passwordEncoder.matches(user.getPwd(), U.getPwd());
		 if(check) {
			 return U;
		 }else {
			 return null;
		 }
		 
	   }
	
	public UserVO checkById(String uid) {
		return UM.checkById(uid);
	}
	
	public int lostPw(UserVO user, String ran2) {
		String encodePassword = passwordEncoder.encode(ran2);
		user.setPwd(encodePassword);
		return UM.lostPw(user);
	}
	
	public int updateUser(UserVO user) {
		String encodePassword = passwordEncoder.encode(user.getPwd());
		user.setPwd(encodePassword);
		return UM.updateUser(user);
		
	}

}
