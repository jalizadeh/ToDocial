package com.jalizadeh.springboot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jalizadeh.springboot.web.repository.TodoRepository;

@Controller
public class HomeController {	
	
	@Autowired
	TodoRepository todoRepository;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String ShowWelcomePage(ModelMap model) {
		model.put("todos", todoRepository.findAll());
		model.put("todoCount", todoRepository.findAll().size());
		model.put("PageTitle", "Home");
		return "home";
	}
}
