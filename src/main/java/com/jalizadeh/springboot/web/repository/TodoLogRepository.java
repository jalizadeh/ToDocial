package com.jalizadeh.springboot.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jalizadeh.springboot.web.model.TodoLog;

public interface TodoLogRepository extends JpaRepository<TodoLog, Long> {
	
	/*
	@Query("select tl from TodoLog tl where tl.todo.id = ?1")
	List<TodoLog> findAllTodoLogsByTodoId(Long todoId);
	*/
}
