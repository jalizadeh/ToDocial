package com.jalizadeh.todocial.api.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace.Principal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.jalizadeh.todocial.system.repository.UserRepository;
import com.jalizadeh.todocial.system.service.UserService;
import com.jalizadeh.todocial.web.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/api/v1/{username}")
	public Principal getMe(Principal user) {
		return user;
	}

	@GetMapping("/api/v1/user")
	public ResponseEntity<List<UserDTO>> getUsers() {
		List<User> users = userRepository.findAll();
		return new ResponseEntity<List<UserDTO>>(users.stream()
				.map(i -> mapUserToDTO(i)).collect(Collectors.toList())
				, HttpStatus.OK);
	}

	@GetMapping("/api/v1/user/{username}")
	public UserDTO getUserByUsername(@PathVariable("username") String username) {
		User user = userRepository.findByUsername(username);
		return mapUserToDTO(user);
	}
	
	//Ambiguous handler methods mapped for '/api/v1/user/1'
	//@GetMapping("/api/v1/user/{id}")
	public UserDTO getUserById(@PathVariable("id") Long id) {
		User user = userRepository.findById(id).orElseGet(null);
		return mapUserToDTO(user);
	}

	
	@AllArgsConstructor
	@Data
	class UserDTO{
		private Long id;
		private String firstname;
		private String lastname;
		private String username;
		private String email;
		private boolean enabled;
		private String photo;
		private List<String> followers;
		private List<String> followings;
	}
	
	private UserDTO mapUserToDTO(User u) {
		if(u == null) return null;
		
		return new UserDTO(
				u.getId(), u.getFirstname(), u.getLastname(), 
				u.getUsername() ,u.getEmail(), u.isEnabled(), u.getPhoto(),
				u.getFollowers().stream().map(i -> i.getUsername()).collect(Collectors.toList()),
				u.getFollowings().stream().map(i -> i.getUsername()).collect(Collectors.toList())
				);
	}
}
