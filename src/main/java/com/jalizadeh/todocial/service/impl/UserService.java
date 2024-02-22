package com.jalizadeh.todocial.service.impl;

import com.jalizadeh.todocial.exception.EmailExistsException;
import com.jalizadeh.todocial.exception.UserAlreadyExistException;
import com.jalizadeh.todocial.model.user.User;
import com.jalizadeh.todocial.repository.user.RoleRepository;
import com.jalizadeh.todocial.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService, ApplicationListener<AuthenticationSuccessEvent> {
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private SessionRegistry sessionRegistry;

	@Autowired
	private TokenService tokenService;
	
	
	public UserService() {
		super();
	}
	
	public String sayHi(String string) {
		return "Hi";
	}

	public boolean isUserAnonymous() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken)
			return true;

		return false;
	}

	public User getUserByPrincipal(Principal principal) {
		return (User)((UsernamePasswordAuthenticationToken)principal).getPrincipal();
	}
	

	public User getAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(authentication == null || authentication.getName().equals("anonymousUser")) {
			return null;
		}
		
		return (User) authentication.getPrincipal();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return findByUsername(username);
	}

	public List<User> findByEnabled(boolean isEnabled){
		return userRepository.findByEnabled(isEnabled);
	}

	public User findById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() ->
						new ResponseStatusException(HttpStatus.NOT_FOUND, "User Id not found: " + id)
				);
	}

	public User findByUsername(String username) {
		return userRepository.findOptionalByUsername(username)
				.orElseThrow(() ->
						new ResponseStatusException(HttpStatus.NOT_FOUND, "Username not found: " + username)
				);
	}	
	
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User save(User user) {
		return userRepository.save(user);
	}
	
	/**
	 * It is used for both registering a new user from both the User & Admin
	 * <br/>
	 * If later more roles are added, it supports them, the role's name comes
	 * from the registering form
	 */
	public User registerNewUserAccount(final User user) throws UserAlreadyExistException, EmailExistsException {
		
		if(usernameExists(user.getUsername())) {
        	throw new UserAlreadyExistException();
        }
		
        if (emailExists(user.getEmail())) {
            throw new EmailExistsException();
        }
        
        User nUser = new User();
        nUser.setFirstname(user.getFirstname());
        nUser.setLastname(user.getLastname());
        nUser.setUsername(user.getUsername());
        nUser.setEmail(user.getEmail());
        String pass = passwordEncoder.encode(user.getPassword());
        nUser.setPassword(pass);
        nUser.setMp(pass);
        //nUser.setUsing2FA(user.isUsing2FA());
        //nUser.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        
        /*
        Role role = new Role();
        if(user.getRole() != null)
        	role = roleRepository.findByName(user.getRole().getName()); //must use existing roles
        else
        	role = roleRepository.findByName("ROLE_USER"); //must use existing roles
        
        nUser.setRole(role);
        */
        nUser.setEnabled(user.isEnabled());
        nUser.setPhoto(user.getPhoto());
        
        return userRepository.save(nUser);
    }


	public void changePassword(User user, String password) {
		String pass = passwordEncoder.encode(password);
		user.setPassword(pass);
		user.setMp(pass);
		userRepository.save(user);
	}
	
	
	public List<String> getAllLoggedinUsersAsString(){
		List<Object> principals = sessionRegistry.getAllPrincipals();
		//User[] users = principals.toArray(new User[principals.size()]);
		return principals.stream()
				.filter(User.class::isInstance)
                .map(User.class::cast)
				.filter(u -> !sessionRegistry.getAllSessions(u, false).isEmpty())
				.map(u -> u.getFirstname() + " " + u.getLastname() + " [" + u.getUsername() + "]")
				.collect(Collectors.toList());
	}
	
	public List<User> getAllLoggedinUsers(){
		List<Object> principals = sessionRegistry.getAllPrincipals();
		
		User[] users = principals.toArray(new User[principals.size()]);
		return Arrays.stream(users)
				.filter(u -> !sessionRegistry.getAllSessions(u, false).isEmpty())
				//.map(u -> u.getFirstname() + " " + u.getLastname() + " [" + u.getUsername() + "]")
				.collect(Collectors.toList());
	}
	
	

	private boolean emailExists(final String email) {
        return userRepository.findByEmail(email) != null;
    }

	private boolean usernameExists(String username) {
		return userRepository.findByUsername(username) != null;
	}


	public void softDelete(String username){
		User user = findByUsername(username);
		user.setEnabled(false);
		save(user);
	}

	public void hardDelete(String username) {
		User user = findByUsername(username);

		//in case user is not activated yet and his token exists
		tokenService.deleteByUser(user);

		userRepository.delete(user);
	}

	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		String userName = ((UserDetails) event.getAuthentication().getPrincipal()).getUsername();
		User user = userRepository.findByUsername(userName);
		user.setLastLoginDate(new Date());
		userRepository.save(user);
	}
}
