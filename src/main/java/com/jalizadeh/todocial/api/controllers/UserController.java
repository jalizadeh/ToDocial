package com.jalizadeh.todocial.api.controllers;

import javax.websocket.server.PathParam;

import org.springframework.boot.actuate.trace.http.HttpTrace.Principal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@GetMapping("/api/v1/me")
	public Principal getMe(Principal user) {
		return user;
	}

	@GetMapping("/api/v1/user")
	public ResponseEntity<String> getUsers() {
		return new ResponseEntity<String>("", HttpStatus.OK);
	}

	@GetMapping("/api/v1/user/{id}")
	public ResponseEntity<String> getUserById(@PathVariable("id") int id) {
		return new ResponseEntity<String>("ID: " + id, HttpStatus.OK);
	}

}
