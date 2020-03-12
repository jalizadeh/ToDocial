package com.jalizadeh.springboot.web.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jalizadeh.springboot.web.model.User;
import com.jalizadeh.springboot.web.model.VerificationToken;
import com.jalizadeh.springboot.web.repository.TokenRepository;
import com.jalizadeh.springboot.web.repository.UserRepository;

@Service
public class TokenService {

	public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";
    
    
	@Autowired
	private TokenRepository tokenRepository;
	
	@Autowired 
	private UserRepository userRepository;
	
	
	public void createVerificationToken(User user, String token) {
		final VerificationToken myToken = 
				new VerificationToken(token, user, new Date());
        tokenRepository.save(myToken);
	}


	
	public VerificationToken getVerificationToken(String token) {
		return tokenRepository.findByToken(token);
	}


	
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
}
