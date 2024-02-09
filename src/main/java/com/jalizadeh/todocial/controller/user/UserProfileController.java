package com.jalizadeh.todocial.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jalizadeh.todocial.service.impl.UserService;

@Controller
public class UserProfileController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/user/profile")
	public String ShowProfile(ModelMap model) {
		model.put("PageTitle", "Your Profile");
		model.put("loggedinUser", userService.getAuthenticatedUser());
		return "user/profile";
	}
	
}
