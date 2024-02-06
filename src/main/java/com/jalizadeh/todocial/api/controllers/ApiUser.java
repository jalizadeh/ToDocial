package com.jalizadeh.todocial.api.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.jalizadeh.todocial.api.controllers.dto.TokenDto;
import com.jalizadeh.todocial.api.controllers.dto.UserDto;
import com.jalizadeh.todocial.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.jalizadeh.todocial.web.repository.VerificationTokenRepository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.jalizadeh.todocial.utils.DataUtils.mapUserToDTO;

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
	public UserDto getMe() {
		User currentUser = userService.GetAuthenticatedUser();
		return mapUserToDTO(currentUser);
	}

	@GetMapping("/api/v1/user")
	public ResponseEntity<List<UserDto>> getUsers() {
		List<User> users = userRepository.findByEnabled(true);
		return new ResponseEntity<>(users.stream().map(DataUtils::mapUserToDTO).collect(Collectors.toList()), HttpStatus.OK);
	}

	@GetMapping("/api/v1/user/{username}")
	public UserDto getUserByUsername(@PathVariable("username") String username) {
		User user = userRepository.findByUsername(username);
		//User currentUser = userService.GetAuthenticatedUser();
		return mapUserToDTO(user);
	}
	
	//Ambiguous handler methods mapped for '/api/v1/user/1'
	//@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
		User user = userRepository.findById(id).orElseGet(null);
		
		if(user == null)
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<UserDto>(mapUserToDTO(user), HttpStatus.OK);
	}
	
	
	@PostMapping("/api/v1/user")
	public ResponseEntity<UserDto> createUser(@RequestBody InputUser user) throws UserAlreadyExistException, EmailExistsException {
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
		return new ResponseEntity<>(mapUserToDTO(registeredUser), HttpStatus.CREATED);
	}
	
	
	@PostMapping("/api/v1/user/{username}/activate")
	public ResponseEntity<String> activateUser(@PathVariable("username") String username,
			@RequestParam("token") String token){
		String tokenStatus = tokenService.validateVerificationToken(TokenService.TOKEN_TYPE_VERIFICATION,token);
		return new ResponseEntity<>(tokenStatus, HttpStatus.ACCEPTED);
	}
	
	
	@GetMapping("/api/v1/user/{username}/activation_token")
	public ResponseEntity<TokenDto> getActivationToken(@PathVariable("username") String username){
		User user = userRepository.findByUsername(username);
		VerificationToken token = vTokenRepository.findByUser(user);
		return new ResponseEntity<>(new TokenDto(token.getToken()) , HttpStatus.OK);
	}

	
	@DeleteMapping("/api/v1/user/{username}")
	public ResponseEntity<String> deleteUser(@PathVariable("username") String username){
		User user = userRepository.findByUsername(username);
		user.setEnabled(false);
		User updated = userRepository.save(user);
		return new ResponseEntity<>(updated.isEnabled() + "", HttpStatus.OK);
	}
	
	@DeleteMapping("/api/v1/user/{username}/db")
	public ResponseEntity<String> deleteUserFromDB(@PathVariable("username") String username){
		User user = userRepository.findByUsername(username);

		if(user == null)
			return new ResponseEntity<>(HttpStatus.OK);
		
		//in case user is not activated yet and the token exists
		VerificationToken token = vTokenRepository.findByUser(user);
		if(token != null) 
			vTokenRepository.delete(token);
		
		userRepository.delete(user);
		return new ResponseEntity<>(HttpStatus.OK);
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

}
