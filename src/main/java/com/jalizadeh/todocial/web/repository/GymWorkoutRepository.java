package com.jalizadeh.todocial.web.repository;

import com.jalizadeh.todocial.web.model.gym.GymPlanDayWorkout;
import com.jalizadeh.todocial.web.model.gym.GymWorkout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GymWorkoutRepository extends JpaRepository<GymWorkout, Long>{

}
