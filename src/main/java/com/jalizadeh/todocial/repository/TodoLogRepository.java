package com.jalizadeh.todocial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jalizadeh.todocial.model.TodoLog;

public interface TodoLogRepository extends JpaRepository<TodoLog, Long> {
	
	/*
	@Query("select tl from TodoLog tl where tl.todo.id = ?1")
	List<TodoLog> findAllTodoLogsByTodoId(Long todoId);
	*/
}
