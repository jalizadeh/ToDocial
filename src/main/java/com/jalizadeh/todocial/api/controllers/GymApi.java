package com.jalizadeh.todocial.api.controllers;

import com.jalizadeh.todocial.web.model.gym.*;
import com.jalizadeh.todocial.web.model.gym.dto.GymWorkoutLogSetRep_DTO;
import com.jalizadeh.todocial.web.repository.*;
import com.jalizadeh.todocial.web.utils.GymUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class GymApi {

    @Autowired
    private GymPlanRepository gymRepository;

    @Autowired
    private GymPlanWeekDayRepository gymWeekRepository;

    @Autowired
    private GymDayRepository gymDayRepository;

    @Autowired
    private GymDayWorkoutRepository gymDayWorkoutRepository;

    @Autowired
    private GymWorkoutLogRepository gymWorkoutLogRepository;

    @GetMapping("/api/v1/gym/plan")
    public List<GymPlan> getAllPlans(){
        return gymRepository.findAll();
    }

    @GetMapping("/api/v1/gym/plan/{planId}/week")
    public List<GymPlanWeekDay> getAllWeeksOfPlan(@PathVariable("planId") Long planId){
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

    @GetMapping("/api/v1/gym/plan/{planId}/week/{week}/day/{dayId}/workout/{workoutId}/log")
    public List<GymWorkoutLog> getAllWorkoutsLogsOfDayWorkout(
            @PathVariable("planId") Long planId,@PathVariable("week") Long week, @PathVariable("dayId") Long dayId,
             @PathVariable("workoutId") Long workoutId){

        //return gymDayWorkoutRepository.findAllByPlanIdAndDayId(planId, dayId);
        Optional<GymDayWorkout> workout = gymDayWorkoutRepository.findById(workoutId);
        if(!workout.isPresent()){
            return null;
        }

        GymDayWorkout foundWorkout = workout.get();
        //return gymWorkoutLogRepository.findAllByWeekAndDayWorkout(week, foundWorkout);
        return null;
    }


    @PostMapping("/api/v1/gym/workoutLogNoteParser")
    public List<GymWorkoutLogSetRep_DTO> logParser(@RequestBody String log){
        return GymUtils.workoutLogNoteParser(log);
    }

}
