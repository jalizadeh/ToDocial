package com.jalizadeh.todocial.web.repository;

import com.jalizadeh.todocial.web.model.gym.GymDayWorkout;
import com.jalizadeh.todocial.web.model.gym.GymPlanWeekDay;
import com.jalizadeh.todocial.web.model.gym.GymWorkoutLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GymWorkoutLogRepository extends JpaRepository<GymWorkoutLog, Long>{

    List<GymWorkoutLog> findAllByDayWorkout(Long workoutId);
    List<GymWorkoutLog> findAllByPwdAndDayWorkout(GymPlanWeekDay pwd, GymDayWorkout workout);
    List<GymWorkoutLog> findAllByPwd(GymPlanWeekDay pwd);

    @Query("SELECT wl FROM GymWorkoutLog wl JOIN wl.dayWorkout dw WHERE dw.workout.id = :workoutId ORDER BY wl.logDate ASC")
    List<GymWorkoutLog> findAllLogsForWorkout(@Param("workoutId") Long workoutId);
}
