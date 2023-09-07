package com.jalizadeh.todocial.web.model.gym;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class GymWorkout {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    private int sets;

    private int repsMin;
    private int repsMax;

    private int restMin;
    private int restMax;

    private String img;

    private String suggestion;

}
