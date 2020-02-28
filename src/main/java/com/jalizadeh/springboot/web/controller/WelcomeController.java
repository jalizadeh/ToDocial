package com.jalizadeh.springboot.web.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jalizadeh.springboot.web.repository.TodoRepository;
import com.jalizadeh.springboot.web.service.UserService;

@Controller
public class WelcomeController {	
	
	@Autowired
	UserService userService;
	
	@Autowired
	TodoRepository todoRepository;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String ShowWelcomePage(ModelMap model, Principal principal) {
        model.put("name", userService.GetUserByPrincipal(principal).getUsername());
		model.put("todoCount", todoRepository.findAll().size());
		model.put("PageTitle", "Welcome");
		return "welcome";
	}
	
	
	
	
	
}
