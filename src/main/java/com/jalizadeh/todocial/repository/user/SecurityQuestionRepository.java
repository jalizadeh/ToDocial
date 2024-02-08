package com.jalizadeh.todocial.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jalizadeh.todocial.model.user.SecurityQuestion;

public interface SecurityQuestionRepository extends 
		JpaRepository<SecurityQuestion, Long> {

	@Query("select s from SecurityQuestion s where s.user.id= ?1")
	SecurityQuestion findByUserId(Long uId);
	
}
