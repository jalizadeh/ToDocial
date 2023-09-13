package com.jalizadeh.todocial.web.repository;

import com.jalizadeh.todocial.web.model.gym.GymPlan;
import com.jalizadeh.todocial.web.model.gym.GymWeek;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GymWeekRepository extends JpaRepository<GymWeek, Long>{

    List<GymWeek> findAllByPlanId(Long id);

}
