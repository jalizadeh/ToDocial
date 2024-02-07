package com.jalizadeh.todocial.repository;

import com.jalizadeh.todocial.model.gym.GymWorkout;
import com.jalizadeh.todocial.model.gym.types.GymMuscleCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GymWorkoutRepository extends JpaRepository<GymWorkout, Long>{

    List<GymWorkout> findAllByMuscleCategoryOrderByName(GymMuscleCategory mc);

    List<GymWorkout> findAllByOrderByName();

    List<GymWorkout> findAllByOrderByMuscleCategory();
}
