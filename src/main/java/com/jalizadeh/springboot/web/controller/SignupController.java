package com.jalizadeh.springboot.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignupController {

	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String SignupMessage(ModelMap model) {
		model.put("PageTitle", "Signup");
		return "signup";
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String SignupNewUser(ModelMap model,
			@RequestParam String firstname,
			@RequestParam String lastname,
			@RequestParam String email) {
		
		if(firstname.length() == 0 || lastname.length()==0) {
			model.put("error", "Fields can't be empty");
			return "signup";
		}
		
		model.put("firstname", firstname);
		model.put("lastname", lastname);
		model.put("email", email);
		return "confirm_email";
	}
}
