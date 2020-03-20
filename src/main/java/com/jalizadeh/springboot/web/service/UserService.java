package com.jalizadeh.springboot.web.service;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jalizadeh.springboot.web.error.EmailExistsException;
import com.jalizadeh.springboot.web.error.UserAlreadyExistException;
import com.jalizadeh.springboot.web.model.User;
import com.jalizadeh.springboot.web.repository.RoleRepository;
import com.jalizadeh.springboot.web.repository.UserRepository;

@Service
//@Transactional
public class UserService implements UserDetailsService {
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private SessionRegistry sessionRegistry;
	
	
	public UserService() {
		super();
	}


	public User GetUserByPrincipal(Principal principal) {
		return (User)((UsernamePasswordAuthenticationToken)principal).getPrincipal();
	}
	

	public User GetAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(authentication.getName().equals("anonymousUser")) {
			return null;
		}
		
		return (User) authentication.getPrincipal();
	}

	
	public UserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException {
		
		User user = userRepository.findByUsername(username);
		
		if(user == null)
			throw new UsernameNotFoundException("User not found");
		
		/*
		return new org.springframework.security.core.userdetails.User(
				user.getUsername(),
				user.getPassword(),
				user.isEnabled(),
				true,
				true,
				true,
				user.getAuthorities()
				);
			*/
		return user;
	}


	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}	
	
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	/**
	 * It is used for both registering a new user from both the User & Admin
	 * <br/>
	 * If later more roles are added, it supports them, the role's name comes
	 * from the registering form
	 */
	public User registerNewUserAccount(final User user) 
    		throws UserAlreadyExistException, EmailExistsException {
		
		if(usernameExists(user.getUsername())) {
        	throw new EmailExistsException("There is an account with this username: " + user.getUsername() + "\nPlease select a different username.");
        }
		
        if (emailExists(user.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email adress: " + user.getEmail() + "\nPlease enter a different email address.");
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
        
        return userRepository.save(nUser);
    }


	public void changePassword(User user, String password) {
		String pass = passwordEncoder.encode(password);
		user.setPassword(pass);
		user.setMp(pass);
		userRepository.save(user);
	}
	
	
	public List<String> getAllLoggedinUsers(){
		List<Object> principals = sessionRegistry.getAllPrincipals();
		
		User[] users = principals.toArray(new User[principals.size()]);
		return Arrays.stream(users)
				.filter(u -> !sessionRegistry.getAllSessions(u, false).isEmpty())
				.map(u -> u.getFirstname() + " " + u.getLastname() + " [" + u.getUsername() + "]")
				.collect(Collectors.toList());
	}
	
	
	
	
	// ~ Methods
	// =======================================
	
	private boolean emailExists(final String email) {
        return userRepository.findByEmail(email) != null;
    }
	
	private boolean usernameExists(String username) {
		return userRepository.findByUsername(username) != null;
	}
}
