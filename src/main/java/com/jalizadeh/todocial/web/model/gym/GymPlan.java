package com.jalizadeh.todocial.web.model.gym;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class GymPlan {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String title;

    @OneToOne
    @JoinColumn(name = "gym_plan_introduction")
    private GymPlanIntroduction gymPlanIntroduction;

    private int progress;

}
