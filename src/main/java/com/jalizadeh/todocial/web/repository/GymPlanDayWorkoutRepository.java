package com.jalizadeh.todocial.web.repository;

import com.jalizadeh.todocial.web.model.gym.GymPlanDayWorkout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GymPlanDayWorkoutRepository extends JpaRepository<GymPlanDayWorkout, Long>{

    List<GymPlanDayWorkout> findAllByPlanId(Long planId);

    List<GymPlanDayWorkout> findAllByPlanIdAndDayId(Long planId, Long dayId);

}
