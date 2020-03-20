package com.jalizadeh.springboot.web.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jalizadeh.springboot.web.model.TodoLog;
import com.jalizadeh.springboot.web.repository.TodoLogRepository;

@Service
public class TodoLogService {

	@Autowired
	private TodoLogRepository todoLogRepository;
	
	public TodoLog saveNewLog(String log) {
		return todoLogRepository.save(new TodoLog(new Date(), log));
	}

	
}
