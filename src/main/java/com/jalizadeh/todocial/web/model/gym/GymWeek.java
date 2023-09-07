package com.jalizadeh.todocial.web.model.gym;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Entity
@Data
public class GymWeek {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    //private List<GymDay> days;

}
