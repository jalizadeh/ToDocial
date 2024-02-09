package com.jalizadeh.todocial.service.impl;

import com.jalizadeh.todocial.model.Test;
import com.jalizadeh.todocial.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;


    public Test save(Test test) {
        return testRepository.save(test);
    }

    public List<Test> findAllTestsByUserId(Long id) {
        return testRepository.findAllByUserId(id);
    }

    public Test findAllTestsByUid(String uid) {
        return testRepository.findAllByUid(uid);
    }
}
