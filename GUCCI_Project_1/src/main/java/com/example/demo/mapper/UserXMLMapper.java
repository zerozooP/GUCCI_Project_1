package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.KakaoVO;
import com.example.demo.vo.NaverVO;
import com.example.demo.vo.UserVO;

@Mapper
public interface UserXMLMapper {
    UserVO selectLogin(UserVO vo);
	int insertUser(UserVO user);
	UserVO getUser(UserVO user);
	UserVO checkById(String uid);
	int updateUser(UserVO user);
	int lostPw(UserVO user);
	int kakaoUser(KakaoVO kakao);
	KakaoVO checkByKakaoId(KakaoVO vo);
	int naverUser(NaverVO naver);
	NaverVO checkByNaverId(NaverVO vo);
}
