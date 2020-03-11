package com.jalizadeh.springboot.web.service;

import java.security.Principal;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
import com.jalizadeh.springboot.web.model.VerificationToken;
import com.jalizadeh.springboot.web.repository.RoleRepository;
import com.jalizadeh.springboot.web.repository.TokenRepository;
import com.jalizadeh.springboot.web.repository.UserRepository;

@Service
//@Transactional
public class UserService implements IUserService {
	
	public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";
	
	
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private TokenRepository tokenRepository;
	
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
        //System.err.println(nUser.getRole().getId() +" / " +nUser.getRole().getName() + " / " + nUser.getRole() );
        if(nUser.getRole() != null)
        	role = roleRepository.getOne(nUser.getRole().getId()); //must use existing roles
        else
        	role = roleRepository.getOne(2L); //must use existing roles
        
        nUser.setRole(role);
        nUser.setEnabled(false);
        return userRepository.save(nUser);
    }


	@Override
	public void createVerificationToken(User user, String token) {
		final VerificationToken myToken = 
				new VerificationToken(token, user, new Date());
        tokenRepository.save(myToken);
	}


	@Override
	public VerificationToken getVerificationToken(String token) {
		return tokenRepository.findByToken(token);
	}


	@Override
	public String validateVerificationToken(String token) {
		VerificationToken vt = tokenRepository.findByToken(token);
		if(vt == null) {
			return TOKEN_INVALID;
		}
		
		User user = vt.getUser();
		user.setMp(user.getPassword());
		
		Calendar cal = Calendar.getInstance();
		if(vt.getExpiryDate().getTime() 
				- cal.getTime().getTime() <= 0 ) {
			tokenRepository.delete(vt); //expired token must be deleted
			return TOKEN_EXPIRED;
		}
		
		user.setEnabled(true);
		tokenRepository.delete(vt); //valid token must be deleted
		userRepository.save(user);
		return TOKEN_VALID;
	}
	
	
	
	//=======================================
	
	private boolean emailExists(final String email) {
        return userRepository.findByEmail(email) != null;
    }
	
	private boolean usernameExists(String username) {
		return userRepository.findByUsername(username) != null;
	}
}
