package com.jalizadeh.todocial.service.registration;

import org.springframework.context.ApplicationEvent;

import com.jalizadeh.todocial.model.user.User;

import lombok.Data;

@Data
public class OnApiRegistrationCompleteEvent extends ApplicationEvent{

	private User user;
	
	public OnApiRegistrationCompleteEvent(User user) {
		super(user);
		this.user = user;
	}
	
}
