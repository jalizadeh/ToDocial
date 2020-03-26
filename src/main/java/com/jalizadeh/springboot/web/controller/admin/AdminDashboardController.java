package com.jalizadeh.springboot.web.controller.admin;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminDashboardController {
	
	@RequestMapping(value="/admin/panel", method=RequestMethod.GET)
	public String ShowAdminPanel(ModelMap model, Principal principal) {
		model.put("PageTitle", "Admin Panel");
		return "admin/panel";
	}
	
	@RequestMapping(value="/admin/todos", method=RequestMethod.GET)
	public String ShowAdminPanel_Todos(ModelMap model, Principal principal) {
		model.put("PageTitle", "Admin > Todos");
		return "admin/todos";
	}
	
}
