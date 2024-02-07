package com.jalizadeh.todocial.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jalizadeh.todocial.model.user.PasswordResetToken;
import com.jalizadeh.todocial.model.user.User;
import com.jalizadeh.todocial.model.user.ActivationToken;
import com.jalizadeh.todocial.repository.user.ActivationTokenRepository;
import com.jalizadeh.todocial.repository.user.PasswordResetTokenRepository;
import com.jalizadeh.todocial.repository.user.UserRepository;

@Service
public class TokenService {

	public static final String TOKEN_INVALID = "invalid";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";
    
    public static final String TOKEN_TYPE_VERIFICATION = "VT";
    public static final String TOKEN_TYPE_PASSWORD_RESET = "PRT";
    
	@Autowired
	private ActivationTokenRepository tokenRepository;
	
	@Autowired
	private PasswordResetTokenRepository prtRepository;
	
	@Autowired 
	private UserRepository userRepository;
	
	
	public void createVerificationToken(User user, String token) {
		final ActivationToken myToken = new ActivationToken(token, user, new Date());
        tokenRepository.save(myToken);
	}

	public ActivationToken findActivationToken(String token) {
		return tokenRepository.findByToken(token);
	}


	//Supports both Activation & Password Reset tokens
	public String validateToken(String type, String token) {
		if(type.equals(TOKEN_TYPE_VERIFICATION)) {
			ActivationToken vt = tokenRepository.findByToken(token);
			if(vt == null) {
				return TOKEN_INVALID;
			}
			
			User user = vt.getUser();
			user.setMp(user.getPassword());
			
			Calendar cal = Calendar.getInstance();
			if(vt.getExpiryDate().getTime() - cal.getTime().getTime() <= 0 ) {
				tokenRepository.delete(vt); //expired token must be deleted
				return TOKEN_EXPIRED;
			}
			
			user.setEnabled(true);
			tokenRepository.delete(vt); //valid token must be deleted
			userRepository.save(user);
			return TOKEN_VALID;
		
		} else { //PRT
			PasswordResetToken prt = prtRepository.findByToken(token);
			if(prt == null) {
				return TOKEN_INVALID;
			}
			
			User user = prt.getUser();
			user.setMp(user.getPassword());
			
			Calendar cal = Calendar.getInstance();
			if(prt.getExpiryDate().getTime() - cal.getTime().getTime() <= 0 ) {
				prtRepository.delete(prt); //expired token must be deleted
				return TOKEN_EXPIRED;
			}
			
			//Changing password is a two-step function
			//1. here I should check if token is valid
			//2. ok, first SecurityContext must be filled with the user, then
			//   the token is removed in controller
			return TOKEN_VALID;
		}
	}

	public ActivationToken findByUser(User user) {
		return tokenRepository.findByUser(user);
	}

	public void delete(ActivationToken token) {
		tokenRepository.delete(token);
	}

	public void deleteByUser(User user) {
		ActivationToken token = tokenRepository.findByUser(user);
		tokenRepository.delete(token);
	}
}
