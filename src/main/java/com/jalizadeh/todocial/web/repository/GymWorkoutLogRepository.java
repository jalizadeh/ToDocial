package com.jalizadeh.todocial.web.repository;

import com.jalizadeh.todocial.web.model.gym.GymDayWorkout;
import com.jalizadeh.todocial.web.model.gym.GymPlan;
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

    //Query to find all sessions for the specified plan. Aggregates all logs for each day and calculate those as a single session
    @Query("SELECT wl FROM GymWorkoutLog wl JOIN wl.pwd pwd WHERE pwd.plan.id = :planId group by wl.pwd ORDER BY wl.logDate DESC")
    List<GymWorkoutLog> findAllSessionsForPlan(@Param("planId") Long planId);

    @Query("SELECT mc, COUNT(*) " +
            "FROM GymWorkoutLog wl " +
            "JOIN wl.pwd pwd " +
            "JOIN wl.dayWorkout dw " +
            "JOIN dw.workout w " +
            "JOIN w.muscleCategory mc " +
            "WHERE pwd.plan.id = :planId " +
            "GROUP BY pwd.plan.id, mc")
    List<Object[]> workoutsByMuscleGroup(@Param("planId") Long planId);
}
