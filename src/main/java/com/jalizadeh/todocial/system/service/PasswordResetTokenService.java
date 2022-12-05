package com.jalizadeh.todocial.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jalizadeh.todocial.web.model.PasswordResetToken;
import com.jalizadeh.todocial.web.model.User;
import com.jalizadeh.todocial.web.repository.PasswordResetTokenRepository;

@Service
public class PasswordResetTokenService {

	@Autowired
	private PasswordResetTokenRepository prtRepository;
	
	public User findUserByToken(String token) {
		return prtRepository.findByToken(token).getUser();
	}

	public void saveNewToken(PasswordResetToken prt) {
		prtRepository.save(prt);
	}
	
	
	public void deleteByToken(String token) {
		//valid token must be deleted
		prtRepository.deleteByToken(token); 
	}
}
