package com.example.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.mapper.MainXMLMapper;
import com.example.demo.vo.CalendarVO;

@Component
public class MainDAO {

	@Autowired
	private MainXMLMapper mapper;

	public List<CalendarVO> getCalendar() {
		
		return mapper.selectAllCalendar();
		
	}
}
