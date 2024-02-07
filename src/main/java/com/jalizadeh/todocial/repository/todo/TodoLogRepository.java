package com.jalizadeh.todocial.repository.todo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jalizadeh.todocial.model.todo.TodoLog;

public interface TodoLogRepository extends JpaRepository<TodoLog, Long> {
	
	/*
	@Query("select tl from TodoLog tl where tl.todo.id = ?1")
	List<TodoLog> findAllTodoLogsByTodoId(Long todoId);
	*/
}
