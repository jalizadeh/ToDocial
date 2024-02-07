package com.jalizadeh.todocial.api.controllers;

import com.jalizadeh.todocial.api.controllers.dto.InputUser;
import com.jalizadeh.todocial.api.controllers.dto.TokenDto;
import com.jalizadeh.todocial.api.controllers.dto.UserDto;
import com.jalizadeh.todocial.system.service.TokenService;
import com.jalizadeh.todocial.system.service.UserService;
import com.jalizadeh.todocial.system.service.registration.OnApiRegistrationCompleteEvent;
import com.jalizadeh.todocial.utils.DataUtils;
import com.jalizadeh.todocial.web.exception.EmailExistsException;
import com.jalizadeh.todocial.web.exception.UserAlreadyExistException;
import com.jalizadeh.todocial.web.model.User;
import com.jalizadeh.todocial.web.model.ActivationToken;
import com.jalizadeh.todocial.web.repository.ActivationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.jalizadeh.todocial.utils.DataUtils.mapUserToDTO;

@RestController
@RequestMapping("/api/v1/user")
public class ApiUser {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ActivationTokenRepository vTokenRepository;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	private TokenService tokenService;

	@GetMapping("/me")
	public UserDto getMe() {
		User currentUser = userService.GetAuthenticatedUser();
		return mapUserToDTO(currentUser);
	}

	@GetMapping()
	public ResponseEntity<List<UserDto>> getUsers() {
		List<User> users = userService.findByEnabled(true);
		return new ResponseEntity<>(users.stream().map(DataUtils::mapUserToDTO).collect(Collectors.toList()), HttpStatus.OK);
	}

	@GetMapping(value = "/{username}")
	public UserDto getUserByUsername(@PathVariable("username") String username) {
		User user = userService.findByUsername(username);
		return mapUserToDTO(user);
	}

	//this is a useless method, it is just here for educational purposes
	@GetMapping(value = "/id/{id}")
	public UserDto getUserById(@PathVariable("id") Long id) {
		User user = userService.findById(id);
		return mapUserToDTO(user);
	}

	@PostMapping()
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


	@PostMapping("/{username}/activate")
	public ResponseEntity<String> activateUser(@RequestParam("token") String token){
		String tokenStatus = tokenService.validateVerificationToken(TokenService.TOKEN_TYPE_VERIFICATION,token);
		return new ResponseEntity<>(tokenStatus, HttpStatus.ACCEPTED);
	}
	
	
	@GetMapping("/{username}/activation_token")
	public ResponseEntity<TokenDto> getActivationToken(@PathVariable("username") String username){
		User user = userService.findByUsername(username);
		ActivationToken token = vTokenRepository.findByUser(user);
		return new ResponseEntity<>(new TokenDto(token.getToken()) , HttpStatus.OK);
	}

	
	@DeleteMapping("/{username}")
	public ResponseEntity<String> deleteUser(@PathVariable("username") String username){
		User user = userService.findByUsername(username);
		user.setEnabled(false);
		User updated = userService.save(user);
		return new ResponseEntity<>(String.valueOf(updated.isEnabled()), HttpStatus.OK);
	}
	
	@DeleteMapping("/{username}/db")
	public ResponseEntity<String> deleteUserFromDB(@PathVariable("username") String username){
		User user = userService.findByUsername(username);

		if(user == null)
			return new ResponseEntity<>(HttpStatus.OK);
		
		//in case user is not activated yet and the token exists
		ActivationToken token = vTokenRepository.findByUser(user);
		if(token != null) 
			vTokenRepository.delete(token);
		
		userService.delete(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
