package com.jalizadeh.todocial.web.model.gym;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Data
public class GymDayWorkout {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "day_id")
    private GymDay day;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "workout_id")
    private GymWorkout workout;

    private int workoutNumber;

    private int sets;

    private int repsMin;
    private int repsMax;

    private int restMin;
    private int restMax;

    @Min(0)
    @Max(100)
    protected int progress;
}
