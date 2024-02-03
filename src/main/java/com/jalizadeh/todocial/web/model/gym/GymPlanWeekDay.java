package com.jalizadeh.todocial.web.model.gym;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GymPlanWeekDay {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    @JsonIgnore
    private GymPlan plan;

    @Min(1)
    private long weekNumber;

    @Min(1)
    @Max(7)
    private long dayNumber;

    @Min(0)
    @Max(100)
    protected int progress;

    private Date workoutDate;


    public GymPlanWeekDay(GymPlan plan, long weekNumber, long dayNumber, int progress) {
        this.plan = plan;
        this.weekNumber = weekNumber;
        this.dayNumber = dayNumber;
        this.progress = progress;
    }
}
