package com.jalizadeh.todocial.web.model.gym;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Data
public class GymWeek extends Progress{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private GymPlan plan;

    @Min(1)
    private int weekNumber;


}
