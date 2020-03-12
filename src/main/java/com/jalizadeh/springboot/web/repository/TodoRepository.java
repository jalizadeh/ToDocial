package com.jalizadeh.springboot.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jalizadeh.springboot.web.model.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {

	@Query("select t from Todo t where t.user.id=:#{principal.id}")
	List<Todo> findAll();

	@Query("select t from Todo t where t.user.id= ?1")
	List<Todo> findAllByUserId(Long id);
}
