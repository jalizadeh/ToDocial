package com.jalizadeh.springboot.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"name", "password"})
public class ProfileController {

	@RequestMapping("/profile")
	public String ShowProfile(ModelMap model) {
		return "profile";
	}
}
