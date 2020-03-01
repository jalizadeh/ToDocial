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
import com.jalizadeh.springboot.web.model.FlashMessage;
import com.jalizadeh.springboot.web.model.User;
import com.jalizadeh.springboot.web.service.IUserService;
import com.jalizadeh.springboot.web.validator.UserValidator;

@Controller
public class SignupController {
	
	@Autowired
	private IUserService iUserService;
	
	

	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String SignupMessage(ModelMap model ) {
		model.addAttribute("user", new User());
		model.put("PageTitle", "Sign up");
		return "signup";
	}
	
	
	//Register new user and handle all errors
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ModelAndView registerUserAccount
	      (@Valid User user, BindingResult result,
	    		  final HttpServletRequest request, Errors errors) throws EmailExistsException{ 

		User registered = null;
		//System.err.println(errors.toString());
		//System.err.println(user.toString());
		
		ValidationUtils.invokeValidator(new UserValidator(), user, errors);
	    
	    if (!result.hasErrors()) {
	        try {
	        	registered = iUserService.registerNewUserAccount(user);
			} catch (Exception e) {
				return new ModelAndView("signup", "flash", 
		    			new FlashMessage(e.getMessage(), FlashMessage.Status.FAILURE));
			}
	    }
	    
	    if(result.hasErrors()) {
	    	//final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	    	List<String> errorMessages = new ArrayList<String>();
	    	for (ObjectError obj : errors.getAllErrors()) {
	    		errorMessages.add(obj.getDefaultMessage());
			}
	    	
	    	return new ModelAndView("signup", "errorMessages", errorMessages);
	    }
	    
	    if (registered == null)
	    	return new ModelAndView("signup", "user", user);
	    
	    
	    return new ModelAndView("confirm_email", "user", registered);
	}
}
