package com.jalizadeh.todocial.api.controllers;

import com.jalizadeh.todocial.web.model.gym.GymPlan;
import com.jalizadeh.todocial.web.model.gym.GymWorkout;
import com.jalizadeh.todocial.web.repository.GymRepository;
import com.jalizadeh.todocial.web.repository.GymWorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiGym {

    @Autowired
    private GymRepository gymRepository;

    @Autowired
    private GymWorkoutRepository gymWorkoutRepository;

    @GetMapping("/api/v1/gym/plan")
    public List<GymPlan> getAllPlans(){
        return gymRepository.findAll();
    }

    @GetMapping("/api/v1/gym/workout")
    public List<GymWorkout> getAllWorkouts(){
        return gymWorkoutRepository.findAll();
    }
}
