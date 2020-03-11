package com.jalizadeh.springboot.web.registration.listener;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.jalizadeh.springboot.web.model.User;
import com.jalizadeh.springboot.web.registration.OnRegistrationCompleteEvent;
import com.jalizadeh.springboot.web.service.IUserService;

@Component
public class RegistrationListener implements 
		ApplicationListener<OnRegistrationCompleteEvent>{

	@Autowired
    private IUserService iUserService;
  
    @Autowired
    private MessageSource messages;
  
    @Autowired
    private JavaMailSender mailSender;
	
	
	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
		this.confirmRegistration(event);
	}

	
	private void confirmRegistration(OnRegistrationCompleteEvent event) {
		User user = event.getUser();
		String token = UUID.randomUUID().toString();
		iUserService.createVerificationToken(user, token);
		
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
