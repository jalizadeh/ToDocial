package com.jalizadeh.springboot.web.controller.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jalizadeh.springboot.web.controller.admin.model.SettingsGeneral;
import com.jalizadeh.springboot.web.controller.admin.model.SettingsGeneralConfig;
import com.jalizadeh.springboot.web.model.FlashMessage;
import com.jalizadeh.springboot.web.model.Privilege;
import com.jalizadeh.springboot.web.model.Role;
import com.jalizadeh.springboot.web.repository.PrivilegeRepository;
import com.jalizadeh.springboot.web.repository.RoleRepository;

@Controller
public class AdminSettingsController {
	
	@Autowired
	private SettingsGeneralConfig settings;
	
	@Autowired
	private RoleRepository roleReporsitory;
	
	@Autowired
	private PrivilegeRepository privilegeRepository;
	
	
	@GetMapping("/admin/settings")
	public String ShowAdminSettings(ModelMap model) {
		model.put("now", new Date());
		model.put("roles", allRoles());
		model.put("languages", languages());
		model.put("settings", settings);
		model.put("PageTitle", "Administrative Settings");
		model.put("settingsObj", fetchLatestSettings());
		return "admin/settings-general";
	}

	
	@PostMapping("/admin/settings")
	public String SaveChanges(ModelMap model, @Valid SettingsGeneral sg, 
			BindingResult result, RedirectAttributes redirectAttributes) {
		if(result.hasErrors()) {
			model.put("flash", 
					new FlashMessage("There is some error",  FlashMessage.Status.danger));
			return "admin/settings-general";
		}
		
		updateSettings(sg);
		redirectAttributes.addFlashAttribute("flash", 
				new FlashMessage("Settings are saved successfully",  FlashMessage.Status.success));
		return "redirect:/admin/settings";
	}
	
	
	
	@GetMapping("/admin/settings/add-role")
	public String showRole(ModelMap model) {
		model.put("settings", settings);
		model.put("PageTitle", "Add new role");
		
		List<Privilege> allPrivileges = privilegeRepository.findAll();
		Role role = new Role();
		role.setPrivileges(allPrivileges);
		model.put("role", role);
		return "admin/role";
	}
	
	@GetMapping("/admin/settings/modify-role")
	public String showSelectedRole(ModelMap model, @RequestParam String name) {
		model.put("settings", settings);
		model.put("PageTitle", "Modify role");		
		model.put("role", roleReporsitory.findByName(name));
		return "admin/role";
	}
	
	@PostMapping("/admin/settings/role")
	public String addNewRole(@Valid Role role, ModelMap model) {
		role.setName("ROLE_"+role.getName().toUpperCase());
		roleReporsitory.save(role);
		return "redirect:/admin/settings";
	}
	
	
	//=======Methods=================
	
	private SettingsGeneral fetchLatestSettings() {
		SettingsGeneral sg = new SettingsGeneral();
		BeanUtils.copyProperties(settings, sg);
		return sg;
	}
	
	
	private void updateSettings(SettingsGeneral sg) {
		BeanUtils.copyProperties(sg, settings);
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
