package com.example.demo.service;


import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import com.example.demo.dao.UserDAO;
import com.example.demo.mapper.UserXMLMapper;
import com.example.demo.vo.NaverLoginApi;
import com.example.demo.vo.NaverVO;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

@Service
public class NaverSVC {
	
        //https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&client_id=MhRVVMd7QR2sBba1Hjbm&client_secret=RL2hRwMHQZ&code=o3n1Txe6lbYvD1XQHH
     
	
	@Autowired
	public UserXMLMapper UM;
	
	@Autowired
	public UserDAO dao;
	
	private HttpSession session;
	
	@Autowired
	public NaverSVC(HttpSession session) {
		this.session = session;
	}
	
	//state: 애플리케이션이 생성한 상태 토큰
	private final static String CLIENT_ID = "MhRVVMd7QR2sBba1Hjbm";
	private final static String CLIENT_SECRET = "RL2hRwMHQZ";
	private final static String REDIRECT_URI = "http://localhost:8080/oauth/naver";
	private final static String SESSION_STATE = "oauth_state";
	/* 프로필 조회 API URL */
	private final static String PROFILE_API_URL = "https://openapi.naver.com/v1/nid/me";
	/* 네이버 아이디로 인증 URL 생성 Method */
	public String getAuthorizationUrl(HttpSession session) {
	/* 세션 유효성 검증을 위하여 난수를 생성 */
	String state = generateRandomString();
	/* 생성한 난수 값을 session에 저장 */
	setSession(session,state);
	/* Scribe에서 제공하는 인증 URL 생성 기능을 이용하여 네아로 인증 URL 생성 */
	OAuth20Service oauthService = new ServiceBuilder()
	.apiKey(CLIENT_ID)
	.apiSecret(CLIENT_SECRET)
	.callback(REDIRECT_URI)
	.state(state) //앞서 생성한 난수값을 인증 URL생성시 사용함
	.build(NaverLoginApi.instance());
	return oauthService.getAuthorizationUrl();
	}
	/* 네이버아이디로 Callback 처리 및 AccessToken 획득 Method */
	public OAuth2AccessToken getAccessToken(HttpSession session, String code, String state) throws IOException{
	/* Callback으로 전달받은 세선검증용 난수값과 세션에 저장되어있는 값이 일치하는지 확인 */
	String sessionState = getSession(session);
			if(StringUtils.pathEquals(sessionState, state)){
				OAuth20Service oauthService = new ServiceBuilder()
				.apiKey(CLIENT_ID)
				.apiSecret(CLIENT_SECRET)
				.callback(REDIRECT_URI)
				.state(state)
				.build(NaverLoginApi.instance());
				/* Scribe에서 제공하는 AccessToken 획득 기능으로 네아로 Access Token을 획득 */
				OAuth2AccessToken accessToken = oauthService.getAccessToken(code);
				System.out.println("oooo");
				return accessToken;
			}
			System.out.println("xxxx");
			return null;
	}
	/* 세션 유효성 검증을 위한 난수 생성기 */
	private String generateRandomString() {
	return UUID.randomUUID().toString();
	}
	/* http session에 데이터 저장 */
	private void setSession(HttpSession session,String state){
	session.setAttribute(SESSION_STATE, state);
	}
	/* http session에서 데이터 가져오기 */
	private static String getSession(HttpSession session){
	return (String) session.getAttribute(SESSION_STATE);
	}
	/* Access Token을 이용하여 네이버 사용자 프로필 API를 호출 */
	public boolean getUserProfile(OAuth2AccessToken oauthToken,Model model) throws Exception{
		OAuth20Service oauthService =new ServiceBuilder()
		.apiKey(CLIENT_ID)
		.apiSecret(CLIENT_SECRET)
		.callback(REDIRECT_URI).build(NaverLoginApi.instance());
		OAuthRequest request = new OAuthRequest(Verb.GET, PROFILE_API_URL, oauthService);
		oauthService.signRequest(oauthToken, request);
		Response response = request.send();
	
		JSONParser parser = new JSONParser();
		 Object obj = parser.parse(response.getBody());
		 JSONObject jsonObj = (JSONObject) obj;
		 //3. 데이터 파싱
		 //Top레벨 단계 _response 파싱
		 JSONObject response_obj = (JSONObject)jsonObj.get("response");
		 System.out.println(response_obj);
		 //response의 nickname값 파싱
		 String uid = (String)response_obj.get("name");
		 String id = (String)response_obj.get("id");
		 String email = (String)response_obj.get("email");
		 System.out.println(uid);
		 
		 NaverVO vo = new NaverVO();
		 vo.setEmail(email);
	     vo.setNaverid(id);
	     vo.setUid(uid);
	 
     if(UM.checkByNaverId(vo)!=null) {
	    	session.setAttribute("email",vo.getEmail());
	    	model.addAttribute("uid",vo.getUid());
	    	return true;
	    }else {
	    	return false;
	    }
	}
	
	public void insertNaverUser(OAuth2AccessToken oauthToken) throws Exception{
		OAuth20Service oauthService =new ServiceBuilder()
		.apiKey(CLIENT_ID)
		.apiSecret(CLIENT_SECRET)
		.callback(REDIRECT_URI).build(NaverLoginApi.instance());
		OAuthRequest request = new OAuthRequest(Verb.GET, PROFILE_API_URL, oauthService);
		oauthService.signRequest(oauthToken, request);
		Response response = request.send();
	
		JSONParser parser = new JSONParser();
		 Object obj = parser.parse(response.getBody());
		 JSONObject jsonObj = (JSONObject) obj;
		 //3. 데이터 파싱
		 //Top레벨 단계 _response 파싱
		 JSONObject response_obj = (JSONObject)jsonObj.get("response");
		 System.out.println(response_obj);
		 //response의 nickname값 파싱
		 String uid = (String)response_obj.get("name");
		 String id = (String)response_obj.get("id");
		 String email = (String)response_obj.get("email");
		 System.out.println(uid);
		 
		 NaverVO vo = new NaverVO();
		 vo.setEmail(email);
	     vo.setNaverid(id);
	     vo.setUid(uid);
	     
	     dao.naverUser(vo);
	     
	}
}
