package com.jalizadeh.springboot.web.controller.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jalizadeh.springboot.web.controller.admin.model.SettingsGeneral;
import com.jalizadeh.springboot.web.controller.admin.model.SettingsGeneralConfig;
import com.jalizadeh.springboot.web.model.FlashMessage;
import com.jalizadeh.springboot.web.model.Role;
import com.jalizadeh.springboot.web.repository.RoleRepository;

@Controller
public class AdminSettingsController {
	
	@Autowired
	private SettingsGeneralConfig settings;
	
	@Autowired
	private RoleRepository roleReporsitory;
	
	
	@GetMapping("/admin/settings")
	public String ShowAdminSettings(ModelMap model) {
		model.put("now", new Date());
		model.put("roles", allRoles());
		model.put("languages", languages());
		model.put("settings", settings);
		model.put("PageTitle", "Administrative Settings");
		model.put("settingsObj", fetchLatestSettings());
		return "admin/settings";
	}

	
	@PostMapping("/admin/settings")
	public String SaveChanges(ModelMap model, @Valid SettingsGeneral sg, 
			BindingResult result, RedirectAttributes redirectAttributes) {
		if(result.hasErrors()) {
			model.put("flash", 
					new FlashMessage("There is some error",  FlashMessage.Status.danger));
			return "admin/settings";
		}
		
		updateSettings(sg);
		redirectAttributes.addFlashAttribute("flash", 
				new FlashMessage("Settings are saved successfully",  FlashMessage.Status.success));
		return "redirect:/admin/settings";
	}
	
	
	//=======Methods=================
	
	private SettingsGeneral fetchLatestSettings() {
		SettingsGeneral sg = new SettingsGeneral();
		sg.setSiteName(settings.getSiteName());
		sg.setSiteDescription(settings.getSiteDescription());
		sg.setFooterCopyright(settings.getFooterCopyright());
		sg.setAnyoneCanRegister(settings.isAnyoneCanRegister());
		sg.setDefaultRole(settings.getDefaultRole());
		sg.setServerLocalTime(settings.getServerLocalTime());
		sg.setDateStructure(settings.getDateStructure());
		sg.setTimeStructure(settings.getTimeStructure());
		sg.setLanguage(settings.getLanguage());
		return sg;
	}
	
	
	private void updateSettings(SettingsGeneral sg) {
		settings.setSiteName(sg.getSiteName());
		settings.setSiteDescription(sg.getSiteDescription());
		settings.setFooterCopyright(sg.getFooterCopyright());
		settings.setAnyoneCanRegister(sg.isAnyoneCanRegister());
		settings.setDefaultRole(sg.getDefaultRole());
		settings.setServerLocalTime(sg.getServerLocalTime());
		settings.setDateStructure(sg.getDateStructure());
		settings.setTimeStructure(sg.getTimeStructure());
		settings.setLanguage(sg.getLanguage());
	}
	
	
	private Map<String,String> allRoles(){
		Map<String,String> roles = new HashMap<String, String>();
		for (Role role: roleReporsitory.findAll()) {
			roles.put(role.getName(),role.getName().replace("ROLE_", ""));
		}
		return roles;
	}
	
	
	private Map<String,String> languages(){
		Map<String,String> l = new HashMap<String, String>();
		l.put("en", "English");
		l.put("it", "Italian");
		return l;
	}
}
