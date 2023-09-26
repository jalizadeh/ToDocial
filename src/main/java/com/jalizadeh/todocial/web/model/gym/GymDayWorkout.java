package com.jalizadeh.todocial.web.model.gym;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Entity
@Data
public class GymDayWorkout {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "day_id")
    @JsonIgnore
    private GymDay day;

    @ManyToOne
    @JoinColumn(name = "workout_id")
    private GymWorkout workout;

    @OneToMany(mappedBy = "dayWorkout")
    private List<GymWorkoutLog> workoutLogs;

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
