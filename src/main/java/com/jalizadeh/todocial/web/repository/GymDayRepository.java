package com.jalizadeh.todocial.web.repository;

import com.jalizadeh.todocial.web.model.gym.GymDay;
import com.jalizadeh.todocial.web.model.gym.GymWeek;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GymDayRepository extends JpaRepository<GymDay, Long>{

    List<GymDay> findAllByPlanId(Long id);

    List<GymDay> findAllByPlanIdOrderByDayNumber(Long id);

    GymDay findByDayNumber(Long planId);
}
