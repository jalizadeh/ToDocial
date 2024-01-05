package com.jalizadeh.todocial.web.repository;

import com.jalizadeh.todocial.web.model.gym.GymDayWorkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GymDayWorkoutRepository extends JpaRepository<GymDayWorkout, Long>{

    List<GymDayWorkout> findAllByDayId(Long dayId);

    @Query("SELECT dw.day.plan.id FROM GymDayWorkout dw WHERE dw.workout.id = :workoutId")
    List<Long> findPlanIdsByWorkoutId(@Param("workoutId") Long workoutId);
}
