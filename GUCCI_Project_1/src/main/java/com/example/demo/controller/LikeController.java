package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.LikeSVC;
import com.example.demo.vo.LikeVO;

@Controller
public class LikeController {
	
	@Autowired
	private LikeSVC svc;
	
	@PostMapping("/like_push")
	@ResponseBody
	public String likePush(LikeVO vo) {
		System.out.println(vo.getBno());
		System.out.println(vo.getLike_uid());
		return String.format("{\"pushed\": %b}", svc.push(vo));
	}
}
