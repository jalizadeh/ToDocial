package com.jalizadeh.todocial.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jalizadeh.todocial.repository.todo.TodoLogRepository;
import com.jalizadeh.todocial.model.todo.TodoLog;

@Service
public class TodoLogService {

	@Autowired
	private TodoLogRepository todoLogRepository;
	
	public TodoLog saveNewLog(String log) {
		return todoLogRepository.save(new TodoLog(new Date(), log));
	}

	
}
