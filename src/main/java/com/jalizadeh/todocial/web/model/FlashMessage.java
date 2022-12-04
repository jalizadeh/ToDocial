package com.jalizadeh.todocial.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FlashMessage {
    private String message;
    private Status status;

    // It matches the CSS in Bootstrap.
    // https://getbootstrap.com/docs/4.4/components/alerts/}
    public static enum Status {
    	primary,
    	secondary,
    	success,
    	danger,
    	warning,
    	info,
    	light,
    	dark
    }

}
