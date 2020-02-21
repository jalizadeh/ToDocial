package com.jalizadeh.springboot.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Signup {

	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String SignupMessage() {
		return "signup";
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String SignupNewUser(ModelMap model,
			@RequestParam String firstname,
			@RequestParam String lastname,
			@RequestParam String email) {
		model.put("firstname", firstname);
		model.put("lastname", lastname);
		model.put("email", email);
		return "confirm_email";
	}
}
