package com.example.demo.controller;

import java.io.IOException;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.example.demo.dao.UserDAO;
import com.example.demo.service.KakaoSVC;
import com.example.demo.service.MailSVC;
import com.example.demo.service.NaverSVC;
import com.example.demo.service.UserSVC;
import com.example.demo.vo.KakaoVO;
import com.example.demo.vo.MailVO;
import com.example.demo.vo.UserVO;
import com.github.scribejava.core.model.OAuth2AccessToken;

@SessionAttributes("uid")
@Controller
public class UserController {
	
	private String apiResult = null;
	
	@Autowired
	private NaverSVC naver;
	
	@Autowired
	private UserDAO dao;
	
	@Autowired
	private UserSVC svc;
	
	@Autowired
	private MailSVC mSVC;
	
	@Autowired
	private KakaoSVC kakao;
	
	
	@Autowired
	HttpSession session;
	
	
	@PostMapping("/insertuser")
	@ResponseBody
	public String insertUser(UserVO user) {
		System.out.println(user);
		return String.format("{\"insertuser\":%b}", dao.insertUser(user));
	}
	
	@PostMapping("/login")
	@ResponseBody
	public String login(UserVO user, Model model) {
		UserVO vo = svc.readLogin(user);
		boolean ok = svc.login(user);
		if(ok) {
			model.addAttribute("uid",user.getUid());
			session.setAttribute("email", vo.getEmail());
			session.setAttribute("phone", vo.getPhone());
			System.out.println(user.getEmail());
		}
		return String.format("{\"ok\":%b}", ok);
	}	
   
    @GetMapping("/oauth/kakao")
    public String kakaoCallback(@RequestParam String code,Model model,KakaoVO vo) {
    	String access_Token = kakao.getAccessToken(code);
    	
        System.out.println("controller access_token : " + access_Token);
              
		boolean ok = kakao.checkUserKakao(access_Token,model);
		if(ok) {
			System.out.println("기존회원");
			return "index";

		}else {
			System.out.println("신규가입");
			kakao.insertKakaoUser(access_Token);
			return "redirect:/login";
		}
    }
		
	 @GetMapping("/oauth/naver")
	 public String callback(@RequestParam String code, @RequestParam String state, HttpSession session,Model model) throws Exception {
	 		 
	 System.out.println("여기는 callback");
	 OAuth2AccessToken oauthToken;
	 oauthToken = naver.getAccessToken(session, code, state);
	 boolean ok = naver.getUserProfile(oauthToken,model);
	
		if(ok) {
			System.out.println("기존회원");
			return "index";

		}else {
			System.out.println("신규가입");
			naver.insertNaverUser(oauthToken);
			return "redirect:/login";
		}
	 }
	
	@GetMapping("/logout")
	@ResponseBody
	public boolean logout(SessionStatus status) {
		status.setComplete(); // 세션에 저장된 모든 데이터를 삭제함
		return status.isComplete();
	}
	
	@PostMapping("/update")
	@ResponseBody
	public String updateUser(UserVO user, Model model) {
		return String.format("{\"update\":%b}", svc.updateUser(user));
	}
	
	@PostMapping("/findpw")
	@ResponseBody
	public String findPw(UserVO user, Model model){
		
		MailVO mail = new MailVO();
        
        Random rd = new Random();//랜덤 객체 생성
        int ran;
        ran = 10000000 + rd.nextInt(85132456);        
        String ran2 = Integer.toString(ran);
       
        
        mail.setAddress(user.getEmail());
        mail.setTitle("임시 비밀번호 발급 이메일입니다.");
        mail.setMessage("임시 비밀번호: "+ran2);

        mSVC.sendMail(mail);
		
		return String.format("{\"findpw\":%b}", svc.lostPw(user,ran2));
	}
	
    @GetMapping("/kakao/logout")
    public String kakaologout(SessionStatus status) {
    	status.setComplete(); // 세션에 저장된 모든 데이터를 삭제함
		status.isComplete();
		return "redirect:/login";
    }
}
