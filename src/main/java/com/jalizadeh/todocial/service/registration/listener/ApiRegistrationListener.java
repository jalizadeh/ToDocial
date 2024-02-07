package com.jalizadeh.todocial.service.registration.listener;

import java.util.UUID;

import com.jalizadeh.todocial.service.TokenService;
import com.jalizadeh.todocial.service.registration.OnApiRegistrationCompleteEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.jalizadeh.todocial.model.user.User;

@Component
public class ApiRegistrationListener implements ApplicationListener<OnApiRegistrationCompleteEvent>{

	@Autowired
	private TokenService tokenService;
  
	@Override
	public void onApplicationEvent(OnApiRegistrationCompleteEvent event) {
		User user = event.getUser();
		String token = UUID.randomUUID().toString();
		tokenService.createVerificationToken(user, token);
	}
	
}
