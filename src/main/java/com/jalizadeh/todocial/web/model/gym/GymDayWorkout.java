package com.jalizadeh.todocial.web.model.gym;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

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

    @OneToMany(mappedBy = "dayWorkout", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<GymWorkoutLog> workoutLogs;

    private int workoutNumber;

    @Min(0)
    private int sets = 3;

    @Min(0)
    private int repsMin = 10;

    @Min(0)
    private int repsMax = 12;

    @Min(0)
    @Max(100)
    protected int progress;
}
