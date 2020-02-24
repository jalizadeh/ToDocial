package com.jalizadeh.springboot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.jalizadeh.springboot.web.service.LoginService;

@Controller
@SessionAttributes({"name", "password"})
public class LoginController {
	
	//Model is exchanged between Controller and View
	
	@Autowired
	LoginService loginService;
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String ShowWelcomePage(ModelMap model) {
		model.put("name", "javad");
		return "welcome";
	}
	
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String ShowLoginPage(ModelMap model) {
		
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
		model.put("password", password);
		return "redirect:/list-todos";
	}
	
}
