package com.jalizadeh.todocial.web.repository;

import com.jalizadeh.todocial.web.model.gym.GymPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GymPlanRepository extends JpaRepository<GymPlan, Long>{

    List<GymPlan> findAllByActiveTrue();

    @Query("select p from GymPlan p where p.active = False and p.completeDate is not null")
    List<GymPlan> findAllCompletedPlans();
}
