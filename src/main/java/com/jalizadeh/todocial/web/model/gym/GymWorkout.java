package com.jalizadeh.todocial.web.model.gym;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class GymWorkout extends Progress {
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

}
