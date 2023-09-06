package com.jalizadeh.todocial.api.controllers;

import com.jalizadeh.todocial.web.model.gym.GymPlan;
import com.jalizadeh.todocial.web.repository.GymRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiGym {

    @Autowired
    private GymRepository gymRepository;

    @GetMapping("/api/v1/gym/plan")
    public List<GymPlan> getAllPlans(){
        return gymRepository.findAll();
    }
}
