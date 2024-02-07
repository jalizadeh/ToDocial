package com.jalizadeh.todocial.repository;

import com.jalizadeh.todocial.model.gym.GymPlanWeekDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GymPlanWeekDayRepository extends JpaRepository<GymPlanWeekDay, Long>{

    List<GymPlanWeekDay> findAllByPlanId(Long id);

    List<GymPlanWeekDay> findAllByPlanIdAndWeekNumber(Long id, Long week);

    Optional<GymPlanWeekDay> findAllByPlanIdAndWeekNumberAndDayNumber(Long id, Long week, Long day);

}
