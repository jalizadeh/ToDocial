package com.jalizadeh.springboot.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jalizadeh.springboot.web.error.EmailExistsException;
import com.jalizadeh.springboot.web.error.UserAlreadyExistException;
import com.jalizadeh.springboot.web.model.FlashMessage;
import com.jalizadeh.springboot.web.model.User;
import com.jalizadeh.springboot.web.service.IUserService;
import com.jalizadeh.springboot.web.validator.UserValidator;

@Controller
public class SignupController {
	
	@Autowired
	private IUserService iUserService;
	
	

	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String SignupMessage(ModelMap model) {
		model.addAttribute("user", new User());
		model.put("PageTitle", "Sign up");
		return "signup";
	}
	
	
	//Register new user and handle all errors
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ModelAndView registerUserAccount
	      (@Valid User user, BindingResult result, Errors errors) 
	    		  throws UserAlreadyExistException, EmailExistsException { 
		
		//System.err.println(errors.toString());
		//System.err.println(user.toString());
		
		ValidationUtils.invokeValidator(new UserValidator(), user, errors);
	    
	    if (!result.hasErrors()) {
	    	try {
	    		User registered = iUserService.registerNewUserAccount(user);
	        	return new ModelAndView("confirm_email", "user", registered);
			} catch (UserAlreadyExistException | 
					EmailExistsException | 
					Exception e) {
				return new ModelAndView("signup", "exception", e.getMessage());
			}
	    }
	    
    	List<String> errorMessages = new ArrayList<String>();
    	for (ObjectError obj : errors.getAllErrors()) {
    		errorMessages.add(obj.getDefaultMessage());
		}
    	
    	return new ModelAndView("signup", "errorMessages", errorMessages);
	}
}
