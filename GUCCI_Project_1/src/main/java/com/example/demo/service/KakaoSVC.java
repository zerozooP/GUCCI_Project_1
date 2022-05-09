package com.example.demo.service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.demo.dao.UserDAO;
import com.example.demo.mapper.UserXMLMapper;
import com.example.demo.vo.KakaoVO;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;



@Service
public class KakaoSVC {
	
	@Autowired
	public UserXMLMapper UM;
	
	@Autowired
	public UserDAO dao;
	
	private HttpSession session;
	
	@Autowired
	public KakaoSVC(HttpSession session) {
		this.session = session;
	}
	
	 public String getAccessToken (String authorize_code) {
	        String access_Token = "";
	        String refresh_Token = "";
	        String reqURL = "https://kauth.kakao.com/oauth/token";
	        
	        try {
	            URL url = new URL(reqURL);
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            
	            //    POST 요청을 위해 기본값이 false인 setDoOutput을 true로
	            conn.setRequestMethod("POST");
	            conn.setDoOutput(true);
	            
	            //    POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
	            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
	            StringBuilder sb = new StringBuilder();
	            sb.append("grant_type=authorization_code");
	            sb.append("&client_id=060b47c024d4f2421c7875dd3681f916");
	            sb.append("&redirect_uri=http://localhost:8080/oauth/kakao");
	            sb.append("&code=" + authorize_code);
	            bw.write(sb.toString());
	            bw.flush();
	            
	            //    결과 코드가 200이라면 성공
	            int responseCode = conn.getResponseCode();
	            System.out.println("responseCode : " + responseCode);
	 
	            //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
	            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            String line = "";
	            String result = "";
	            
	            while ((line = br.readLine()) != null) {
	                result += line;
	            }
	            System.out.println("response body : " + result);
	            
	            //    Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
	            JsonParser parser = new JsonParser();
	            JsonElement element = parser.parse(result);
	            
	            access_Token = element.getAsJsonObject().get("access_token").getAsString();
	            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();
	            
	            System.out.println("access_token : " + access_Token);
	            System.out.println("refresh_token : " + refresh_Token);
	            
	            br.close();
	            bw.close();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } 
	        
	        return access_Token;
	    }
	 
	 public void insertKakaoUser(String token) {

			String reqURL = "https://kapi.kakao.com/v2/user/me";

		    //access_token을 이용하여 사용자 정보 조회
		    try {
		       URL url = new URL(reqURL);
		       HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		       conn.setRequestMethod("POST");
		       conn.setDoOutput(true);
		       conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성, access_token전송

		       //결과 코드가 200이라면 성공
		       int responseCode = conn.getResponseCode();
		       System.out.println("responseCode : " + responseCode);

		       //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
		       BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		       String line = "";
		       String result = "";

		       while ((line = br.readLine()) != null) {
		           result += line;
		       }
		       System.out.println("response body : " + result);

		       //Gson 라이브러리로 JSON파싱
		       JsonParser parser = new JsonParser();
		       JsonElement element = parser.parse(result);

		       int id = element.getAsJsonObject().get("id").getAsInt();
		       String nickname = element.getAsJsonObject().get("properties").getAsJsonObject().get("nickname").getAsString();
		       boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
		       String email = "";
		       if(hasEmail){
		           email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
		       }

		       System.out.println("id : " + id);
		       System.out.println("email : " + email);
		       
		       KakaoVO vo = new KakaoVO();
		       
		       vo.setEmail(email);
		       vo.setKakaoid(id);
		       vo.setUid(nickname);
		       
		       dao.kakaoUser(vo);
		       
		       br.close();

		       } catch (IOException e) {
		            e.printStackTrace();
		       }
		 }
	 
	 public boolean checkUserKakao(String token,Model model) {
		 	
		 	KakaoVO vo = new KakaoVO();
		 
			String reqURL = "https://kapi.kakao.com/v2/user/me";

		    //access_token을 이용하여 사용자 정보 조회
		    try {
		       URL url = new URL(reqURL);
		       HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		       conn.setRequestMethod("POST");
		       conn.setDoOutput(true);
		       conn.setRequestProperty("Authorization", "Bearer " + token); //전송할 header 작성, access_token전송

		       //결과 코드가 200이라면 성공
		       int responseCode = conn.getResponseCode();
		       System.out.println("responseCode : " + responseCode);

		       //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
		       BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		       String line = "";
		       String result = "";

		       while ((line = br.readLine()) != null) {
		           result += line;
		       }
		       System.out.println("response body : " + result);

		       //Gson 라이브러리로 JSON파싱
		       JsonParser parser = new JsonParser();
		       JsonElement element = parser.parse(result);

		       int id = element.getAsJsonObject().get("id").getAsInt();
		       String nickname = element.getAsJsonObject().get("properties").getAsJsonObject().get("nickname").getAsString();
		       boolean hasEmail = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("has_email").getAsBoolean();
		       String email = "";
		       if(hasEmail){
		           email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
		       }

		       System.out.println("id : " + id);
		       System.out.println("email : " + email);
		       
		       vo.setEmail(email);
		       vo.setKakaoid(id);
		       vo.setUid(nickname);

		       } catch (IOException e) {
		            e.printStackTrace();
		       }

		    if(UM.checkByKakaoId(vo)!=null) {
		    	session.setAttribute("email",vo.getEmail());
		    	model.addAttribute("uid",vo.getUid());
		    	return true;
		    }else {
		    	return false;
		    }
		 }
	 
	
}
