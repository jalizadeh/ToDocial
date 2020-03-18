package com.jalizadeh.springboot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jalizadeh.springboot.web.service.UserService;

@Controller
public class ProfileController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/profile")
	public String ShowProfile(ModelMap model) {
		model.put("PageTitle", "Your Profile");
		model.put("loggedinUser", userService.GetAuthenticatedUser());
		return "profile";
	}
}
