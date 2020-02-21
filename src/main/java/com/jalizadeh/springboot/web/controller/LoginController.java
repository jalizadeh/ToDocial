package com.jalizadeh.springboot.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	
	//Model is exchanged between Controller and View
	
	@RequestMapping("/login")
	public String LoginMessage(@RequestParam String name, ModelMap model) {
		model.put("name", name);
		return "login";
	}
}
