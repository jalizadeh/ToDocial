package com.jalizadeh.todocial.web.model.gym;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Entity
@Data
public class GymDay {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String focus;

    //private List<GymWorkout> workouts;
}
