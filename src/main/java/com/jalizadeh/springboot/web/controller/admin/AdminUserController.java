package com.jalizadeh.springboot.web.controller.admin;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jalizadeh.springboot.web.model.User;
import com.jalizadeh.springboot.web.repository.UserRepository;
import com.jalizadeh.springboot.web.service.UserService;

@Controller
public class AdminUserController {

	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(value="/admin/change_user_state", method=RequestMethod.GET)
	public String ActivateUser(ModelMap model
			, Principal principal
			, @RequestParam Long id) {
		User user = userService.GetUserByPrincipal(principal);
		model.put("user", user);
		model.put("PageTitle", "Admin > Modify User");
		
		if(user.getRole().getName().equals("ROLE_ADMIN")) {
			User foundUser = userRepository.findById(id).get();
			foundUser.setEnabled(!foundUser.isEnabled()); 
			foundUser.setMp(foundUser.getPassword());
			System.err.println(foundUser);
			userRepository.save(foundUser);
			
			return "redirect:/admin/users";
		}
			
		return "error";
	}
	
	
	
}
