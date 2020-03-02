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
		model.put("PageTitle", "Admin > Users");
		
		if(user.getRole().getName().equals("ROLE_ADMIN")) {
			model.put("all_users", userRepository.findAll());
			model.put("users_count", userRepository.count());
			return "admin/users";
		}
		
		return "error";
	}
	
	
	@RequestMapping(value="/admin/todos", method=RequestMethod.GET)
	public String ShowAdminPanel_Todos(ModelMap model, Principal principal) {
		User user = userService.GetUserByPrincipal(principal);
		model.put("user", user);
		model.put("PageTitle", "Admin > Todos");
		
		if(user.getRole().getName().equals("ROLE_ADMIN"))
			return "admin/todos";
		
		return "error";
	}
	
}
