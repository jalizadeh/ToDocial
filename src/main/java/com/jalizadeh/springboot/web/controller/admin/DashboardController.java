package com.jalizadeh.springboot.web.controller.admin;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jalizadeh.springboot.web.model.User;
import com.jalizadeh.springboot.web.repository.UserRepository;
import com.jalizadeh.springboot.web.service.UserService;

@Controller
public class DashboardController {

	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(value="/admin/panel", method=RequestMethod.GET)
	public String ShowAdminPanel(ModelMap model, Principal principal) {
		User loggedinUser = userService.GetUserByPrincipal(principal);
		model.put("loggedinUser", loggedinUser);
		model.put("PageTitle", "Admin Panel");
		
		if(loggedinUser.getRole().getName().equals("ROLE_ADMIN"))
			return "admin/panel";
		
		return "error";
	}
	
	
	
	
	
	@RequestMapping(value="/admin/todos", method=RequestMethod.GET)
	public String ShowAdminPanel_Todos(ModelMap model, Principal principal) {
		User loggedinUser = userService.GetUserByPrincipal(principal);
		model.put("loggedinUser", loggedinUser);
		model.put("PageTitle", "Admin > Todos");
		
		if(loggedinUser.getRole().getName().equals("ROLE_ADMIN"))
			return "admin/todos";
		
		return "error";
	}
	
}
