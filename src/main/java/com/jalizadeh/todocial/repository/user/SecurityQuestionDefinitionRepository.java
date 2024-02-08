package com.jalizadeh.todocial.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jalizadeh.todocial.model.user.SecurityQuestionDefinition;

public interface SecurityQuestionDefinitionRepository extends JpaRepository<SecurityQuestionDefinition, Long> {

}
