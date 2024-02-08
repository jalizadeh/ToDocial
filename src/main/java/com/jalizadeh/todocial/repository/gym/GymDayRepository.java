package com.jalizadeh.todocial.repository.gym;

import com.jalizadeh.todocial.model.gym.GymDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GymDayRepository extends JpaRepository<GymDay, Long>{

    List<GymDay> findAllByPlanId(Long id);

    List<GymDay> findAllByPlanIdOrderByDayNumber(Long id);

    GymDay findByDayNumber(Long planId);

    GymDay findByPlanIdAndDayNumber(Long planId, Long day);
}
