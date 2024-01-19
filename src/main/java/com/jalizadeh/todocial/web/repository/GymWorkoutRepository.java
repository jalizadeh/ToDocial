package com.jalizadeh.todocial.web.repository;

import com.jalizadeh.todocial.web.model.Todo;
import com.jalizadeh.todocial.web.model.gym.GymWorkout;
import com.jalizadeh.todocial.web.model.gym.types.GymMuscleCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface GymWorkoutRepository extends JpaRepository<GymWorkout, Long>{

    List<GymWorkout> findAllByMuscleCategoryOrderByName(GymMuscleCategory mc);

    List<GymWorkout> findAllByOrderByName();
}
