package com.jalizadeh.todocial.web.model.gym;

import com.jalizadeh.todocial.web.model.gym.types.GymEquipment;
import com.jalizadeh.todocial.web.model.gym.types.GymMuscleCategory;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class GymWorkout {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    private String img;

    private String suggestion;

    private GymMuscleCategory muscleCategory;

    private GymEquipment equipment;

    //used for custom item label in the page's Options tag
    public String getFullLabel(){
        return id + " - " + name;
    }


}
