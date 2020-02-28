package com.jalizadeh.springboot.web.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.jalizadeh.springboot.web.model.User;


public interface UserServiceInterface extends UserDetailsService {
	User findByUsername(String username);
}
