package com.jalizadeh.springboot.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jalizadeh.springboot.web.model.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {

}
