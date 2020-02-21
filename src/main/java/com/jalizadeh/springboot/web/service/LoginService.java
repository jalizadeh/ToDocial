package com.jalizadeh.springboot.web.service;

import org.springframework.stereotype.Component;

@Component
public class LoginService {

	public boolean validateUser(String username, String password) {
		return username.equalsIgnoreCase("javad") & 
				password.equalsIgnoreCase("12345");
	}
}
