package com.jalizadeh.todocial.service.impl;

import com.jalizadeh.todocial.model.user.SecurityQuestion;
import com.jalizadeh.todocial.model.user.SecurityQuestionDefinition;
import com.jalizadeh.todocial.repository.user.SecurityQuestionDefinitionRepository;
import com.jalizadeh.todocial.repository.user.SecurityQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityQuestionService {

    @Autowired
    private SecurityQuestionDefinitionRepository sqdRepository;

    @Autowired
    private SecurityQuestionRepository sqRepository;


    public List<SecurityQuestionDefinition> findAllSQD() {
        return sqdRepository.findAll();
    }

    public SecurityQuestion findSQByUserId(Long id) {
        return sqRepository.findByUserId(id);
    }

    public SecurityQuestionDefinition findSQById(Long id) {
        return sqdRepository.findById(id).get();
    }

    public void saveSQ(SecurityQuestion sq) {
        sqRepository.save(sq);
    }
}
