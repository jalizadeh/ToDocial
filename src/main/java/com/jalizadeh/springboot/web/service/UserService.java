package com.jalizadeh.springboot.web.service;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user == null)
			throw new UsernameNotFoundException("User not found");
		
		return user;
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}	
	
	
	@Override
    public User registerNewUserAccount(final User user) {
		if(usernameExists(user.getUsername())) {
        	throw new UserAlreadyExistException("There is an account with this username: " + user.getUsername() + "\nPlease select a different username.");
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
        Role role = roleRepository.getOne(2L); //must use existing roles
        nUser.setRole(role);
        nUser.setEnabled(false);
        
        return userRepository.save(nUser);
    }

	private boolean emailExists(final String email) {
        return userRepository.findByEmail(email) != null;
    }
	
	private boolean usernameExists(String username) {
		return userRepository.findByUsername(username) != null;
	}
}
