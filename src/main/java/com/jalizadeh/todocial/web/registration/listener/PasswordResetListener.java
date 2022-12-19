package com.jalizadeh.todocial.web.registration.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.jalizadeh.todocial.web.registration.OnPasswordResetEvent;

@Component
public class PasswordResetListener implements ApplicationListener<OnPasswordResetEvent>{
	
	@Autowired
    private MessageSource messages;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	public void onApplicationEvent(OnPasswordResetEvent event) {
		String email = event.getPrt().getUser().getEmail();
		String token = event.getPrt().getToken();
		
		String recipientAddress = email;
		String subject = "Reset Password";
		String resetUrl = event.getAppUrl() + "/reset-password?token=" + token;
		String message = "Click on the following link to reset your password:";
		
		SimpleMailMessage smm = new SimpleMailMessage();
		smm.setTo(recipientAddress);
		smm.setSubject(subject);
		smm.setText(message + "\r\n" + "http://localhost:8080" + resetUrl);
		mailSender.send(smm);
	}

}
