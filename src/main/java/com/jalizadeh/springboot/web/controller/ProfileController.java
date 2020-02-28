package com.jalizadeh.springboot.web.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jalizadeh.springboot.web.service.UserService;

@Controller
public class ProfileController {

	@Autowired
	UserService userService;
	
	@RequestMapping("/profile")
	public String ShowProfile(ModelMap model, Principal principal) {
		model.put("name", userService.GetUserByPrincipal(principal).getUsername());
		model.put("PageTitle", "Your Profile");
		return "profile";
	}
}
