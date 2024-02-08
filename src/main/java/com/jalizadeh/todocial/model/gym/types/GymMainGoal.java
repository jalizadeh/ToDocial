package com.jalizadeh.todocial.model.gym.types;

public enum GymMainGoal {
    BUILD_MUSCLE    ("BM"),
    LOSE_WEIGHT     ("LW"),
    FITNESS         ("F"),
    MASS_GAIN       ("MG"),
    POWER_LIFTING   ("PL");


    private String code;

    private GymMainGoal(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }
}
