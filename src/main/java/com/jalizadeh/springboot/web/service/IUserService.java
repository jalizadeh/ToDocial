package com.jalizadeh.springboot.web.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.jalizadeh.springboot.web.error.EmailExistsException;
import com.jalizadeh.springboot.web.error.UserAlreadyExistException;
import com.jalizadeh.springboot.web.model.User;


public interface IUserService extends UserDetailsService {
	User findByUsername(String username);
	
	User registerNewUserAccount(User user) 
			throws UserAlreadyExistException, EmailExistsException;
	
}
