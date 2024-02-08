package com.jalizadeh.todocial.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminDashboardController {
	
	@RequestMapping(value="/admin/panel", method=RequestMethod.GET)
	public String ShowAdminPanel(ModelMap model) {
		model.put("PageTitle", "Admin Panel");
		return "admin/panel";
	}
	
	
}
