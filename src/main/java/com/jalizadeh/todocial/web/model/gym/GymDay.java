package com.jalizadeh.todocial.web.model.gym;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class GymDay {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String focus;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "plan_id")
    @JsonIgnore
    private GymPlan plan;

    @Transient
    private List<GymDayWorkout> dayWorkouts = new ArrayList<>();

    @Min(1)
    @Max(7)
    private int dayNumber;

    @Min(1)
    private int totalWorkouts;

    @Min(0)
    @Max(100)
    protected int progress;
}
