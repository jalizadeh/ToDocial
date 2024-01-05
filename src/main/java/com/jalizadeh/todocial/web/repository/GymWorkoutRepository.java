package com.jalizadeh.todocial.web.repository;

import com.jalizadeh.todocial.web.model.Todo;
import com.jalizadeh.todocial.web.model.gym.GymWorkout;
import com.jalizadeh.todocial.web.model.gym.types.GymMuscleCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GymWorkoutRepository extends JpaRepository<GymWorkout, Long>{

    List<GymWorkout> findAllByMuscleCategory(GymMuscleCategory mc);

}
