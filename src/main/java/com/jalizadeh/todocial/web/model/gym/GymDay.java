package com.jalizadeh.todocial.web.model.gym;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Data
public class GymDay {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String focus;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private GymPlan plan;

    @Min(1)
    @Max(7)
    private Long dayNumber;

    @Min(0)
    @Max(100)
    protected int progress;
}
