package com.jalizadeh.todocial.web.registration.listener;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.jalizadeh.todocial.web.model.User;
import com.jalizadeh.todocial.web.registration.OnRegistrationCompleteEvent;
import com.jalizadeh.todocial.web.service.TokenService;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent>{

	@Autowired
	private TokenService tokenService;
  
    @Autowired
    private MessageSource messages;
  
    @Autowired
    private JavaMailSender mailSender;
	
	
	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
		User user = event.getUser();
		String token = UUID.randomUUID().toString();
		tokenService.createVerificationToken(user, token);
		
		String recipientAddress = user.getEmail();
		String subject = "Confirm Registratoin";
		String confirmationUrl
			= event.getAppUrl() + "/registration-confirm?token=" + token;
		String message = messages.getMessage("message.regSucc", null, event.getLocale());
		
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(recipientAddress);
		email.setSubject(subject);
		email.setText(message + "\r\n" + "http://localhost:8080" + confirmationUrl);
		mailSender.send(email);
	}
}
