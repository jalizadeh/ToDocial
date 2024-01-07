package com.jalizadeh.todocial.web.model.gym.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GymWorkoutStats {

    private double average;
    private double max;
    private double min;

}
