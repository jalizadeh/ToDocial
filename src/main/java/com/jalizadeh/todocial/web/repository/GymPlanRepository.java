package com.jalizadeh.todocial.web.repository;

import com.jalizadeh.todocial.web.model.gym.GymPlan;
import com.jalizadeh.todocial.web.model.gym.types.GymTrainingLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GymPlanRepository extends JpaRepository<GymPlan, Long>{

    List<GymPlan> findAllByActiveTrue();

    @Query("select p from GymPlan p where p.active = False and p.completeDate is not null")
    List<GymPlan> findAllCompletedPlans();

    @Query("SELECT p FROM GymPlan p JOIN p.gymPlanIntroduction pi WHERE p.title like %:query% or pi.moreInfo like %:query%")
    List<GymPlan> searchAll(@Param("query") String query);

    @Query("SELECT p FROM GymPlan p JOIN p.gymPlanIntroduction pi WHERE pi.trainingLevel = :trainingLevel")
    List<GymPlan> findAllByTrainingLevel(@Param("trainingLevel") GymTrainingLevel level);
}
