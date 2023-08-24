package com.jalizadeh.todocial.web.controller;

import com.jalizadeh.todocial.system.repository.TodoRepository;
import com.jalizadeh.todocial.web.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jalizadeh.todocial.web.controller.admin.model.SettingsGeneralConfig;

import java.util.List;

@Controller
public class HomeController {	
	
	@Autowired
	private SettingsGeneralConfig settings;

	@Autowired
	private TodoRepository todoRepository;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String ShowWelcomePage(ModelMap model) {

		List<Todo> allByPubliccTrue = todoRepository.findAllByPubliccTrue();

		model.put("todos", allByPubliccTrue);
		model.put("settings", settings);
		model.put("PageTitle", "Home");
		return "home";
	}
}
