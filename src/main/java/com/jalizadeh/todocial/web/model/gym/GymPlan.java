package com.jalizadeh.todocial.web.model.gym;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class GymPlan {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String title;

    //on Gym homepage, I need to have quick access to Plan's details
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gym_plan_introduction")
    private GymPlanIntroduction gymPlanIntroduction;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "plan")
    private List<GymDay> days = new ArrayList<>();

    @Min(1)
    private int numberOfWeeks;

    @Min(1)
    @Max(7)
    private int numberOfDays;

    @Min(0)
    private long completedDays;

    private boolean active;

    private Date startDate;

    private Date completeDate;

    @Min(0)
    @Max(100)
    protected int progress;

}
