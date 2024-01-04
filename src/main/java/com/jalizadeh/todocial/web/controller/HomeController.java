package com.jalizadeh.todocial.web.controller;

import com.jalizadeh.todocial.system.repository.TodoRepository;
import com.jalizadeh.todocial.system.repository.UserRepository;
import com.jalizadeh.todocial.web.controller.admin.model.SettingsGeneralConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {	
	
	@Autowired
	private SettingsGeneralConfig settings;

	@Autowired
	private TodoRepository todoRepository;

	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String ShowWelcomePage(ModelMap model) {
		model.put("todos", todoRepository.findAllByPubliccTrue());
		model.put("users", userRepository.findByEnabled(true));
		model.put("settings", settings);
		model.put("PageTitle", "Home");
		return "home";
	}
}
