package com.jalizadeh.todocial.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminSecurityController {
	
	@GetMapping("/admin/settings/security")
	public String ShowAdminSettings(ModelMap model) {
		model.put("PageTitle", "Administrative Settings");
		return "admin/settings-security";
	}
	
}
