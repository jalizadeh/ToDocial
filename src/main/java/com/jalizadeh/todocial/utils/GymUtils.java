package com.jalizadeh.todocial.utils;

import com.jalizadeh.todocial.model.gym.dto.GymWorkoutLogSetRep_DTO;

import java.util.ArrayList;
import java.util.List;

public class GymUtils {

    public static List<GymWorkoutLogSetRep_DTO> workoutLogNoteParser(String log){

        if(log.isEmpty() || log.trim().length() == 0)
            return null;

        int DEFAULT_REPS = 12;
        String[] sets;
        sets = log.split(",");

        List<GymWorkoutLogSetRep_DTO> setRepList = new ArrayList<>();

        for(String s : sets){
            if(s.contains("x")){
                String[] set_rep = s.split("x");
                double set = Double.parseDouble(set_rep[0]);
                int repCount = Integer.parseInt(set_rep[1]);
                for(int i = 0; i < repCount; i++){
                    setRepList.add(new GymWorkoutLogSetRep_DTO(set, DEFAULT_REPS));
                }
            } else if(s.contains(":")){
                String[] nestedSets = s.split(":");
                double nestedSet = Double.parseDouble(nestedSets[0]);

                String[] nestedReps = nestedSets[1].split("-");
                for(int i = 0; i< nestedReps.length; i++){
                    setRepList.add(new GymWorkoutLogSetRep_DTO(nestedSet,Integer.parseInt(nestedReps[i])));
                }
            } else {
                setRepList.add(new GymWorkoutLogSetRep_DTO(Double.parseDouble(s), DEFAULT_REPS));
            }
        }

        return setRepList;
    }

    public static String parseRawInput(String text) {
        text = text.replace("%2C", ",");
        text = text.replace("%3A", ":");
        return text;
    }
}
