package com.jalizadeh.todocial.web.model.gym;

import com.jalizadeh.todocial.web.model.gym.types.GymMainGoal;
import com.jalizadeh.todocial.web.model.gym.types.GymTrainingLevel;
import com.jalizadeh.todocial.web.model.gym.types.GymWorkoutType;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class GymPlanIntroduction {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String moreInfo;

    private GymMainGoal mainGoal;

    @Enumerated(EnumType.STRING) //TODO: this solution is not efficient
    private GymWorkoutType workoutType;

    @Enumerated(EnumType.STRING)
    private GymTrainingLevel trainingLevel;

}
