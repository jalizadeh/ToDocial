package com.jalizadeh.todocial.api.controllers;

import com.jalizadeh.todocial.web.model.gym.GymDay;
import com.jalizadeh.todocial.web.model.gym.GymPlan;
import com.jalizadeh.todocial.web.model.gym.GymWeek;
import com.jalizadeh.todocial.web.model.gym.GymDayWorkout;
import com.jalizadeh.todocial.web.repository.GymDayRepository;
import com.jalizadeh.todocial.web.repository.GymPlanRepository;
import com.jalizadeh.todocial.web.repository.GymWeekRepository;
import com.jalizadeh.todocial.web.repository.GymDayWorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiGym {

    @Autowired
    private GymPlanRepository gymRepository;

    @Autowired
    private GymWeekRepository gymWeekRepository;

    @Autowired
    private GymDayRepository gymDayRepository;

    @Autowired
    private GymDayWorkoutRepository gymDayWorkoutRepository;

    @GetMapping("/api/v1/gym/plan")
    public List<GymPlan> getAllPlans(){
        return gymRepository.findAll();
    }

    @GetMapping("/api/v1/gym/plan/{planId}/week")
    public List<GymWeek> getAllWeeksOfPlan(@PathVariable("planId") Long planId){
        return gymWeekRepository.findAllByPlanId(planId);
    }

    @GetMapping("/api/v1/gym/plan/{planId}/day")
    public List<GymDay> getAllDaysOfPlan(@PathVariable("planId") Long planId){
        return gymDayRepository.findAllByPlanId(planId);
    }

    @GetMapping("/api/v1/gym/plan/{planId}/workout")
    public List<GymDayWorkout> getAllWorkoutsOfPlanAndDay(@PathVariable("planId") Long planId){
        //return gymPlanDayWorkoutRepository.findAllByPlanId(planId);
        return null;
    }

    @GetMapping("/api/v1/gym/plan/{planId}/day/{dayId}/workout")
    public List<GymDayWorkout> getAllWorkoutsOfPlanAndDay(@PathVariable("planId") Long planId,
                                                          @PathVariable("dayId") Long dayId){

        //return gymDayWorkoutRepository.findAllByPlanIdAndDayId(planId, dayId);
        return null;
    }
}
