package com.jalizadeh.todocial.api;

import com.jalizadeh.todocial.model.gym.*;
import com.jalizadeh.todocial.model.gym.dto.GymWorkoutLogSetRep_DTO;
import com.jalizadeh.todocial.service.impl.GymService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GymApi {

    @Autowired
    private GymService gymService;

    @GetMapping("/api/v1/gym/plan")
    public List<GymPlan> getAllPlans(){
        return gymService.findAll();
    }

    @GetMapping("/api/v1/gym/plan/{planId}/week")
    public List<GymPlanWeekDay> getAllWeeksOfPlan(@PathVariable("planId") Long planId){
        return gymService.findAllWeeksByPlanId(planId);
    }

    @GetMapping("/api/v1/gym/plan/{planId}/day")
    public List<GymDay> getAllDaysOfPlan(@PathVariable("planId") Long planId){
        return gymService.findAllDaysByPlanId(planId);
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

        /*
        //return gymDayWorkoutRepository.findAllByPlanIdAndDayId(planId, dayId);
        Optional<GymDayWorkout> workout = gymDayWorkoutRepository.findById(workoutId);
        if(!workout.isPresent()){
            return null;
        }

        GymDayWorkout foundWorkout = workout.get();
        //return gymWorkoutLogRepository.findAllByWeekAndDayWorkout(week, foundWorkout);
         */
        return null;
    }


    @PostMapping("/api/v1/gym/workoutLogNoteParser")
    public List<GymWorkoutLogSetRep_DTO> parseLogNote(@RequestBody String log){
        return gymService.parseLogNote(log);
    }

}
