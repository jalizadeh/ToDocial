package com.jalizadeh.springboot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jalizadeh.springboot.web.service.LoginService;

@Controller
public class LoginController {
	
	//Model is exchanged between Controller and View
	
	@Autowired
	LoginService loginService;
	
	//if it is GET, show the login page
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String ShowLoginPage(ModelMap model) {
		//model.put("name", name);
		return "login";
	}
	
	
	//if it is POST go to welcome page
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String ShowWelcomePage(ModelMap model,
			@RequestParam String name,
			@RequestParam String password) {
		boolean isValidated = loginService.validateUser(name, password);
		
		if(!isValidated) {
			model.put("error", "Username or password is wrong");
			return "login";
		}
			
		model.put("name", name);
		return "welcome";
	}
	
}
