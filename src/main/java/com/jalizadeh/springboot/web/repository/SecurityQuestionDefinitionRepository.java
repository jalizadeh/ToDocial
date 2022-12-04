package com.jalizadeh.springboot.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jalizadeh.springboot.web.model.SecurityQuestionDefinition;

public interface SecurityQuestionDefinitionRepository extends 
		JpaRepository<SecurityQuestionDefinition, Long> {

}
