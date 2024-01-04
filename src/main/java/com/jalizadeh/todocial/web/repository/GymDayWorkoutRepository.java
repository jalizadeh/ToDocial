package com.jalizadeh.todocial.web.repository;

import com.jalizadeh.todocial.web.model.gym.GymDayWorkout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GymDayWorkoutRepository extends JpaRepository<GymDayWorkout, Long>{

    List<GymDayWorkout> findAllByDayId(Long dayId);

}
