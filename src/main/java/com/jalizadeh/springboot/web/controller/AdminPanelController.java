package com.jalizadeh.springboot.web.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jalizadeh.springboot.web.model.User;
import com.jalizadeh.springboot.web.service.UserService;

@Controller
public class AdminPanelController {

	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/admin/panel", method=RequestMethod.GET)
	public String ShowAdminPanel(ModelMap model, Principal principal) {
		User user = userService.GetUserByPrincipal(principal);
		model.put("user", user);
		model.put("PageTitle", "Admin Panel");
		
		if(user.getRole().getName().equals("ROLE_ADMIN"))
			return "admin/panel";
		
		return "error";
	}
	
	
	@RequestMapping(value="/admin/users", method=RequestMethod.GET)
	public String ShowAdminPanel_Users(ModelMap model, Principal principal) {
		User user = userService.GetUserByPrincipal(principal);
		model.put("user", user);
		model.put("PageTitle", "Admin Panel");
		
		if(user.getRole().getName().equals("ROLE_ADMIN"))
			return "admin/users";
		
		return "error";
	}
	
	
	@RequestMapping(value="/admin/todos", method=RequestMethod.GET)
	public String ShowAdminPanel_Todos(ModelMap model, Principal principal) {
		User user = userService.GetUserByPrincipal(principal);
		model.put("user", user);
		model.put("PageTitle", "Admin Panel");
		
		if(user.getRole().getName().equals("ROLE_ADMIN"))
			return "admin/todos";
		
		return "error";
	}
	
}
