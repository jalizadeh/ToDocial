package com.jalizadeh.todocial.system.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jalizadeh.todocial.system.repository.TodoLogRepository;
import com.jalizadeh.todocial.web.model.TodoLog;

@Service
public class TodoLogService {

	@Autowired
	private TodoLogRepository todoLogRepository;
	
	public TodoLog saveNewLog(String log) {
		return todoLogRepository.save(new TodoLog(new Date(), log));
	}

	
}
