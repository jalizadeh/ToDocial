package com.jalizadeh.todocial.web.model.gym;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
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

    @ToString.Exclude
    @OneToMany(mappedBy = "day", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JsonIgnore
    private List<GymDayWorkout> dayWorkouts = new ArrayList<>();

    @Min(1)
    @Max(7)
    private long dayNumber;

    @Min(1)
    private long totalWorkouts;

    @Min(0)
    @Max(100)
    protected long progress;
}
