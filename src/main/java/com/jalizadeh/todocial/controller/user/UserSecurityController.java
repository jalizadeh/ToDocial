package com.jalizadeh.todocial.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jalizadeh.todocial.service.impl.UserService;

@Controller
public class UserSecurityController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/user/security")
	public String ShowProfile(ModelMap model) {
		model.put("PageTitle", "Settings");
		model.put("loggedinUser", userService.GetAuthenticatedUser());
		return "user/security";
	}
	
}
