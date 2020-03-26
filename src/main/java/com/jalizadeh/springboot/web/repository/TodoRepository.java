package com.jalizadeh.springboot.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jalizadeh.springboot.web.model.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {

	//this is used for current loggedin user
	@Query("select t from Todo t where t.user.id=:#{principal.id}")
	List<Todo> findAll();
	
	List<Todo> findAllByUserIdAndPubliccTrue(Long id);

	Todo findOneById(Long id);
	
	List<Todo> findAllByUserId(Long id);
}
