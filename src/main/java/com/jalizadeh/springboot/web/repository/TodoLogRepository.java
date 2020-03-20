package com.jalizadeh.springboot.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jalizadeh.springboot.web.model.TodoLog;

public interface TodoLogRepository extends JpaRepository<TodoLog, Long> {
	

}
