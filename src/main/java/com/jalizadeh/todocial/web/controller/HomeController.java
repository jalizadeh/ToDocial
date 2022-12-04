package com.jalizadeh.todocial.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jalizadeh.todocial.web.controller.admin.model.SettingsGeneralConfig;

@Controller
public class HomeController {	
	
	@Autowired
	private SettingsGeneralConfig settings;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String ShowWelcomePage(ModelMap model) {
		model.put("settings", settings);
		model.put("PageTitle", "Home");
		return "home";
	}
}
