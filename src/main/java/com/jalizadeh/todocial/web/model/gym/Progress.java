package com.jalizadeh.todocial.web.model.gym;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class Progress {

    @Min(0)
    @Max(100)
    private int progress;

}
