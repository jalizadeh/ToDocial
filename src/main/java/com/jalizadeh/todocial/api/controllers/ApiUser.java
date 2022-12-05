package com.jalizadeh.todocial.api.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace.Principal;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jalizadeh.todocial.system.repository.UserRepository;
import com.jalizadeh.todocial.system.service.TokenService;
import com.jalizadeh.todocial.system.service.UserService;
import com.jalizadeh.todocial.system.service.registration.OnApiRegistrationCompleteEvent;
import com.jalizadeh.todocial.web.exception.EmailExistsException;
import com.jalizadeh.todocial.web.exception.UserAlreadyExistException;
import com.jalizadeh.todocial.web.model.User;
import com.jalizadeh.todocial.web.model.VerificationToken;
import com.jalizadeh.todocial.web.registration.OnRegistrationCompleteEvent;
import com.jalizadeh.todocial.web.repository.VerificationTokenRepository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
public class ApiUser {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private VerificationTokenRepository vTokenRepository;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	private TokenService tokenService;

	@GetMapping("/api/v1/user/me")
	public UserDTO getMe() {
		User currentUser = userService.GetAuthenticatedUser();
		return mapUserToDTO(currentUser);
	}

	@GetMapping("/api/v1/user")
	public ResponseEntity<List<UserDTO>> getUsers() {
		List<User> users = userRepository.findByEnabled(true);
		return new ResponseEntity<List<UserDTO>>(users.stream()
				.map(i -> mapUserToDTO(i)).collect(Collectors.toList())
				, HttpStatus.OK);
	}

	@GetMapping("/api/v1/user/{username}")
	public UserDTO getUserByUsername(@PathVariable("username") String username) {
		User user = userRepository.findByUsername(username);
		//User currentUser = userService.GetAuthenticatedUser();
		return mapUserToDTO(user);
	}
	
	//Ambiguous handler methods mapped for '/api/v1/user/1'
	//@GetMapping("/{id}")
	public UserDTO getUserById(@PathVariable("id") Long id) {
		User user = userRepository.findById(id).orElseGet(null);
		return mapUserToDTO(user);
	}
	
	
	@PostMapping("/api/v1/user")
	public ResponseEntity<UserDTO> createUser(@RequestBody InputUser user) throws UserAlreadyExistException, EmailExistsException {
		User newUser = new User();
		newUser.setFirstname(user.getFirstname());
		newUser.setLastname(user.getLastname());
		newUser.setUsername(user.getUsername());
		newUser.setEmail(user.getEmail());
		newUser.setPassword(user.getPassword());
		newUser.setEnabled(false);
		newUser.setPhoto("default.jpg");
		User registeredUser = userService.registerNewUserAccount(newUser);

		//generate verification token in async
		eventPublisher.publishEvent(new OnApiRegistrationCompleteEvent(registeredUser));
		return new ResponseEntity<UserDTO>(mapUserToDTO(registeredUser), HttpStatus.CREATED);
	}
	
	
	@PostMapping("/api/v1/user/{username}/activate")
	public ResponseEntity<String> activateUser(@PathVariable("username") String username,
			@RequestParam("token") String token){
		String tokenStatus = tokenService.validateVerificationToken(
				tokenService.TOKEN_TYPE_VERIFICATION,token);
		return new ResponseEntity<String>(tokenStatus, HttpStatus.OK);
	}
	
	
	@GetMapping("/api/v1/user/{username}/activation_token")
	public ResponseEntity<TokenDTO> getActivationToken(@PathVariable("username") String username){
		User user = userRepository.findByUsername(username);
		VerificationToken token = vTokenRepository.findByUser(user);
		return new ResponseEntity<TokenDTO>(new TokenDTO(token.getToken()) , HttpStatus.ACCEPTED);
	}

	
	@DeleteMapping("/api/v1/user/{username}")
	public ResponseEntity<String> deleteUser(@PathVariable("username") String username){
		User user = userRepository.findByUsername(username);
		user.setEnabled(false);
		User updated = userRepository.save(user);
		return new ResponseEntity<String>(updated.isEnabled() + "", HttpStatus.OK);
	}
	
	
	@AllArgsConstructor
	@Data
	static class UserDTO{
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
	
	@AllArgsConstructor
	@Data
	static class TokenDTO{
		private String token;
	}
	
	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	static class InputUser{
		private String firstname;
		private String lastname;
		private String username;
		private String email;
		private String password;
	}
	
	private UserDTO mapUserToDTO(User u) {
		if(u == null) return null;
		
		return new UserDTO(
				u.getId(), u.getFirstname(), u.getLastname(), 
				u.getUsername() ,u.getEmail(), u.isEnabled(), u.getPhoto(),
				u.getFollowers() != null ? u.getFollowers().stream().map(i -> i.getUsername()).collect(Collectors.toList()) : new ArrayList<>(),
				u.getFollowings() != null ? u.getFollowings().stream().map(i -> i.getUsername()).collect(Collectors.toList()) : new ArrayList<>()
				);
	}
	
}
