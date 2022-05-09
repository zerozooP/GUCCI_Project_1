package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.ReplySVC;
import com.example.demo.vo.ReplyVO;

@Controller
public class ReplyController {
	
	@Autowired
	private ReplySVC svc;
	
	@PostMapping("/reply_add")
	@ResponseBody
	public String replyAdd(ReplyVO vo) {
		return String.format("{\"added\": %b}", svc.add(vo));
	}
	
	@PostMapping("/reply_delete")
	@ResponseBody
	public String replyEdit(ReplyVO vo) {
		return String.format("{\"deleted\": %b}", svc.delete(vo));
	}
	
}
