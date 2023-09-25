package com.jalizadeh.todocial.web.model.gym;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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

    //@ManyToMany(cascade = CascadeType.ALL)
    //@JoinTable(name = "gym_day_workout", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "day_id"))
    @Transient
    private List<GymDayWorkout> dayWorkouts = new ArrayList<>();

    @Min(1)
    @Max(7)
    private Long dayNumber;

    @Min(0)
    @Max(100)
    protected int progress;
}
