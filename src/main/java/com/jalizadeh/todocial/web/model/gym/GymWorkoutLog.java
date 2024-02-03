package com.jalizadeh.todocial.web.model.gym;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.ToString;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Data
public class GymWorkoutLog {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pwd_id")
    @JsonIgnore
    private GymPlanWeekDay pwd;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "day_workout_id")
    private GymDayWorkout dayWorkout;

    @Min(1)
    private int setNumber;

    @Min(0)
    private double weight;

    @Min(1)
    private int reps;

    private Date logDate;

}
