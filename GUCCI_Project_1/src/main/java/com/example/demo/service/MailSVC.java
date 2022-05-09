package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.vo.MailVO;

@Service
@Component
public class MailSVC {

	@Autowired    
	private JavaMailSender mailSender;

	    public void sendMail(MailVO mail) {
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo(mail.getAddress());
	        message.setFrom("PWadmin"); //from 값을 설정하지 않으면 application.yml의 username값이 설정됩니다.
	        message.setSubject(mail.getTitle());
	        message.setText(mail.getMessage());

	        mailSender.send(message);
	    }

	}

