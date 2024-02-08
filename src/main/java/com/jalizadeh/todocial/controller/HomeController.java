package com.jalizadeh.todocial.controller;

import com.jalizadeh.todocial.repository.todo.TodoRepository;
import com.jalizadeh.todocial.repository.user.UserRepository;
import com.jalizadeh.todocial.model.settings.SettingsGeneralConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {	
	
	@Autowired
	private SettingsGeneralConfig settings;

	@Autowired
	private TodoRepository todoRepository;

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/")
	public String showHomePage(ModelMap model) {
		model.put("todos", todoRepository.findAllByIsPublicTrue());
		model.put("users", userRepository.findByEnabled(true));
		model.put("settings", settings);
		model.put("PageTitle", "Home");
		return "home";
	}
}
