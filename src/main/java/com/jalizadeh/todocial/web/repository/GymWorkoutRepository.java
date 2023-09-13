package com.jalizadeh.todocial.web.repository;

import com.jalizadeh.todocial.web.model.gym.GymPlan;
import com.jalizadeh.todocial.web.model.gym.GymWorkout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GymWorkoutRepository extends JpaRepository<GymWorkout, Long>{

    List<GymWorkout> findAllByPlanId(Long planId);

    List<GymWorkout> findAllByPlanIdAndDayId(Long planId, Long dayId);

}
