package com.jalizadeh.todocial.web.model.gym;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Data
public class GymPlan extends Progress{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String title;

    @OneToOne
    @JoinColumn(name = "gym_plan_introduction")
    private GymPlanIntroduction gymPlanIntroduction;

    @Min(1)
    private int numberOfWeeks;

    @Min(1)
    @Max(7)
    private int numberOfDays;

    private boolean active;

}
