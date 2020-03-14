package com.jalizadeh.springboot.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.jalizadeh.springboot.web.error.EmailExistsException;
import com.jalizadeh.springboot.web.error.UserAlreadyExistException;
import com.jalizadeh.springboot.web.model.FlashMessage;
import com.jalizadeh.springboot.web.model.User;
import com.jalizadeh.springboot.web.registration.OnRegistrationCompleteEvent;
import com.jalizadeh.springboot.web.service.IUserService;
import com.jalizadeh.springboot.web.service.TokenService;
import com.jalizadeh.springboot.web.service.UserService;
import com.jalizadeh.springboot.web.validator.UserValidator;

@Controller
public class SignupController {
	
	@Autowired
	private IUserService iUserService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	

	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String SignupMessage(ModelMap model) {
		model.addAttribute("user", new User());
		model.put("PageTitle", "Sign up");
		return "signup";
	}
	
	
	//Register new user and handle all errors
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ModelAndView registerUserAccount (@Valid User user, 
			BindingResult result, Errors errors, WebRequest request) 
	    	throws UserAlreadyExistException, EmailExistsException { 
		
		ValidationUtils.invokeValidator(new UserValidator(), user, errors);
	    
		if(result.hasErrors()) {
			List<String> errorMessages = new ArrayList<String>();
	    	for (ObjectError obj : errors.getAllErrors()) {
	    		errorMessages.add(obj.getDefaultMessage());
			}
	    	return new ModelAndView("signup", "errorMessages", errorMessages);
		}
		
		User registered = null;
		
    	try {
    		registered = iUserService.registerNewUserAccount(user);
    		
    		//The process of creating token and sending email is asynchronous
    		// and is handled by eventPublisher
    		String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent
            		(registered, appUrl, request.getLocale()));
            
    	} catch (UserAlreadyExistException | EmailExistsException | 
				Exception e) {
			return new ModelAndView("signup", "exception", e.getMessage());
		}
    	
    	return new ModelAndView("confirm_email", "registeredUser", registered);
	}
	
	
	@RequestMapping(value="/registration-confirm", method=RequestMethod.GET)
	public String confirmRegistration(ModelMap model, WebRequest request,
			@RequestParam("token") String token) {
		
		Locale locale = request.getLocale();
		model.put("user", new User());
		
		String tokenStatus = tokenService.validateVerificationToken(
				tokenService.TOKEN_TYPE_VERIFICATION,token);
		
		switch (tokenStatus) {
			case "valid":
				model.put("flash", 
						new FlashMessage("Your email is verified successfully.\nYou can login now.", 
								FlashMessage.Status.success));
				break;
			case "invalid":
				model.put("flash", 
						new FlashMessage("There is something wrong with your token.<br/>Please login and follow the instructions.", 
								FlashMessage.Status.danger));
				break;
			case "expired":
				model.put("flash", 
						new FlashMessage("Your email verification token is expired.<br/>Please login and follow the instructions.", 
								FlashMessage.Status.danger));
				break;
	
			default:
				break;
		}
		
		return "login";
	}
}
