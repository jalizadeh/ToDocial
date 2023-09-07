package com.jalizadeh.todocial.web.model.gym;

import com.jalizadeh.todocial.web.model.gym.types.*;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

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

    private int programDuration;

    private int daysPerWeek;

    private int timePerWorkout;

    //private List<GymEquipment> equipmentRequired;

    private byte targetGender;

    //private List<GymSupplement> recommendedSupplements;

}
