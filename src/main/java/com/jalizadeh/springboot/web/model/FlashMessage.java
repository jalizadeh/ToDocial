package com.jalizadeh.springboot.web.model;

public class FlashMessage {
    private String message;
    private Status status;

    public FlashMessage(String message, Status status) {
        this.message = message;
        this.status = status;
    }


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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
