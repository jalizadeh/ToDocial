package com.jalizadeh.todocial.web.repository;

import com.jalizadeh.todocial.web.model.Test;
import com.jalizadeh.todocial.web.model.gym.GymPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GymRepository extends JpaRepository<GymPlan, Long>{

    List<GymPlan> findAllByActiveTrue();
}
