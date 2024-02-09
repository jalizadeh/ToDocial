package com.jalizadeh.todocial.api;

import com.jalizadeh.todocial.model.user.dto.InputUser;
import com.jalizadeh.todocial.model.user.dto.TokenDto;
import com.jalizadeh.todocial.model.user.dto.UserDto;
import com.jalizadeh.todocial.service.impl.TokenService;
import com.jalizadeh.todocial.service.impl.UserService;
import com.jalizadeh.todocial.service.registration.OnApiRegistrationCompleteEvent;
import com.jalizadeh.todocial.utils.DataUtils;
import com.jalizadeh.todocial.exception.EmailExistsException;
import com.jalizadeh.todocial.exception.UserAlreadyExistException;
import com.jalizadeh.todocial.model.user.ActivationToken;
import com.jalizadeh.todocial.model.user.User;
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
public class UserApi {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	@GetMapping("/me")
	public UserDto getMe() {
		User currentUser = userService.getAuthenticatedUser();
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
	public ResponseEntity<?> createUser(@RequestBody InputUser user) throws UserAlreadyExistException, EmailExistsException {
		User newUser = new User();
		newUser.setFirstname(user.getFirstname());
		newUser.setLastname(user.getLastname());
		newUser.setUsername(user.getUsername());
		newUser.setEmail(user.getEmail());
		newUser.setPassword(user.getPassword());
		newUser.setEnabled(false);
		newUser.setPhoto("default.jpg");

		try{
			User registeredUser = userService.registerNewUserAccount(newUser);

			//generate verification token in async
			eventPublisher.publishEvent(new OnApiRegistrationCompleteEvent(registeredUser));
			return new ResponseEntity<>(mapUserToDTO(registeredUser), HttpStatus.CREATED);

		} catch (UserAlreadyExistException e){
			return new ResponseEntity<>("The username already exists", HttpStatus.CONFLICT);

		} catch (EmailExistsException e){
			return new ResponseEntity<>("The email already exists", HttpStatus.CONFLICT);
		}
	}


	@PostMapping("/{username}/activate")
	public ResponseEntity<String> activateUser(@RequestParam("token") String token){
		String tokenStatus = tokenService.validateToken(TokenService.TOKEN_TYPE_VERIFICATION, token);
		return new ResponseEntity<>(tokenStatus, HttpStatus.ACCEPTED);
	}
	
	
	@GetMapping("/{username}/activation_token")
	public ResponseEntity<TokenDto> getActivationToken(@PathVariable("username") String username){
		User user = userService.findByUsername(username);
		ActivationToken token = tokenService.findByUser(user);
		return new ResponseEntity<>(new TokenDto(token.getToken()) , HttpStatus.OK);
	}

	
	@DeleteMapping("/{username}")
	public ResponseEntity<?> deleteUser(@PathVariable("username") String username){
		userService.softDelete(username);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/{username}/db")
	public ResponseEntity<String> deleteUserFromDB(@PathVariable("username") String username){
		userService.hardDelete(username);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
