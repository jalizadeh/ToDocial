package com.jalizadeh.todocial.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jalizadeh.todocial.web.model.Test;

public interface TestRepository extends JpaRepository<Test, Long>{

	Test findAllByUid(String uid);
	
	
	List<Test> findAllByUserId(Long id);
}
