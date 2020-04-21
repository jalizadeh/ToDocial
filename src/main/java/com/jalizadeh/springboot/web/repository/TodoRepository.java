package com.jalizadeh.springboot.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jalizadeh.springboot.web.model.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {

	//this is used for current loggedin user
	@Query("select t from Todo t where t.user.id=:#{principal.id}")
	List<Todo> findAllByLoggedinUser();
	
	List<Todo> findAllByUserId(Long id);

	List<Todo> findAllByUserIdAndPubliccTrue(Long id);
	
	@Query("select t from Todo t where t.user.id=:#{principal.id} and t.completed = true and t.canceled = false")
	List<Todo> getAllCompleted();
	
	@Query("select t from Todo t where t.user.id=:#{principal.id} and t.completed = false and t.canceled = false")
	List<Todo> getAllNotCompleted();
	
	@Query("select t from Todo t where t.user.id=:#{principal.id} and t.canceled = true")
	List<Todo> getAllCanceled();

}
