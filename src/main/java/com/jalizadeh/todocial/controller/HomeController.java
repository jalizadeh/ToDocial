package com.jalizadeh.todocial.controller;

import com.jalizadeh.todocial.repository.todo.TodoRepository;
import com.jalizadeh.todocial.repository.user.UserRepository;
import com.jalizadeh.todocial.model.settings.SettingsGeneralConfig;
import com.jalizadeh.todocial.service.impl.TodoService;
import com.jalizadeh.todocial.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {	
	
	@Autowired
	private SettingsGeneralConfig settings;

	@Autowired
	private TodoService todoService;

	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String showHomePage(ModelMap model) {
		model.put("todos", todoService.findAllTodosByIsPublicTrue());
		model.put("users", userService.findByEnabled(true));
		model.put("settings", settings);
		model.put("PageTitle", "Home");
		return "home";
	}
}
