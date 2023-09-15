package com.jalizadeh.todocial.web.model.gym;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Data
public class GymPlanDayWorkout {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private GymPlan plan;

    @ManyToOne
    @JoinColumn(name = "day_id")
    private GymDay day;

    private String name;

    private int sets;

    private int repsMin;
    private int repsMax;

    private int restMin;
    private int restMax;

    private String img;

    private String suggestion;

    @Min(0)
    @Max(100)
    protected int progress;
}
