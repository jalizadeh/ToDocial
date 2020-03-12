package com.jalizadeh.springboot.web.service;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jalizadeh.springboot.web.error.EmailExistsException;
import com.jalizadeh.springboot.web.error.UserAlreadyExistException;
import com.jalizadeh.springboot.web.model.Role;
import com.jalizadeh.springboot.web.model.User;
import com.jalizadeh.springboot.web.repository.RoleRepository;
import com.jalizadeh.springboot.web.repository.UserRepository;

@Service
//@Transactional
public class UserService implements IUserService {
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	public User GetUserByPrincipal(Principal principal) {
		return (User)((UsernamePasswordAuthenticationToken)principal).getPrincipal();
	}
	
	
	public User GetAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		
		return (User) userDetails;
	}
	

	@Override
	public UserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException {
		
		User user = userRepository.findByUsername(username);
		if(user == null)
			throw new UsernameNotFoundException("User not found");
		
		return user;
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}	
	
	
	/**
	 * It is used for both registering a new user from both the User & Admin
	 * <br/>
	 * If later more roles are added, it supports them, the role's name comes
	 * from the registering form
	 */
	@Override
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
        
        
        Role role = new Role();
        if(user.getRole() != null)
        	role = roleRepository.findByName(user.getRole().getName()); //must use existing roles
        else
        	role = roleRepository.findByName("ROLE_USER"); //must use existing roles
        
        nUser.setRole(role);
        nUser.setEnabled(user.isEnabled());
        
        return userRepository.save(nUser);
    }


	
	
	
	
	//=======================================
	
	private boolean emailExists(final String email) {
        return userRepository.findByEmail(email) != null;
    }
	
	private boolean usernameExists(String username) {
		return userRepository.findByUsername(username) != null;
	}
}
