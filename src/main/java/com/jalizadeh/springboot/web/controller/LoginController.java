package com.jalizadeh.springboot.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	
	//Model is exchanged between Controller and View
	
	//if it is GET, show the login page
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String ShowLoginPage(ModelMap model) {
		//model.put("name", name);
		return "login";
	}
	
	
	//if it is POST go to welcome page
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String ShowWelcomePage(ModelMap model,
			@RequestParam String name) {
		model.put("name", name);
		return "welcome";
	}
	
}
