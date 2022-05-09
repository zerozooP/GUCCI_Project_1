package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.MainDAO;
import com.example.demo.vo.CalendarVO;

@Service
public class MainSVC{

	@Autowired
	private MainDAO dao;

	public List<CalendarVO> getCalendar() {
		 

		return dao.getCalendar();
		
	}
}
