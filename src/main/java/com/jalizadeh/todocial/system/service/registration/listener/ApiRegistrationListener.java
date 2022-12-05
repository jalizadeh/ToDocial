package com.jalizadeh.todocial.system.service.registration.listener;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.jalizadeh.todocial.system.service.TokenService;
import com.jalizadeh.todocial.system.service.registration.OnApiRegistrationCompleteEvent;
import com.jalizadeh.todocial.web.model.User;

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
