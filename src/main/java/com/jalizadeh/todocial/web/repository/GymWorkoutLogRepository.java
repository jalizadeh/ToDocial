package com.jalizadeh.todocial.web.repository;

import com.jalizadeh.todocial.web.model.gym.GymDayWorkout;
import com.jalizadeh.todocial.web.model.gym.GymPlanWeekDay;
import com.jalizadeh.todocial.web.model.gym.GymWorkoutLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GymWorkoutLogRepository extends JpaRepository<GymWorkoutLog, Long>{

    List<GymWorkoutLog> findAllByDayWorkout(Long workoutId);

    List<GymWorkoutLog>  findAllByPwdAndDayWorkout(GymPlanWeekDay pwd, GymDayWorkout workout);

    List<GymWorkoutLog>  findAllByPwd(GymPlanWeekDay pwd);
}
