package com.jalizadeh.springboot.web.controller.admin;

import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jalizadeh.springboot.web.error.EmailExistsException;
import com.jalizadeh.springboot.web.error.UserAlreadyExistException;
import com.jalizadeh.springboot.web.model.FlashMessage;
import com.jalizadeh.springboot.web.model.Role;
import com.jalizadeh.springboot.web.model.User;
import com.jalizadeh.springboot.web.repository.RoleRepository;
import com.jalizadeh.springboot.web.repository.UserRepository;
import com.jalizadeh.springboot.web.service.IUserService;
import com.jalizadeh.springboot.web.service.UserService;
import com.jalizadeh.springboot.web.validator.UserValidator;

@Controller
public class AdminUserController {

	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	IUserService iUserService;
	
	@RequestMapping(value="/admin/users", method=RequestMethod.GET)
	public String ShowAdminPanel_Users(ModelMap model, Principal principal) {
		User loggedinUser = userService.GetUserByPrincipal(principal);
		model.put("loggedinUser", loggedinUser);
		model.put("PageTitle", "Admin > Users");
		
		if(loggedinUser.getRole().getName().equals("ROLE_ADMIN")) {
			model.put("all_users", userRepository.findAll());
			model.put("users_count", userRepository.count());
			return "admin/users";
		}
		
		return "error";
	}
	
	
	@RequestMapping(value="/admin/change_user_state", method=RequestMethod.GET)
	public String ActivateUser(ModelMap model
			, Principal principal
			, @RequestParam Long id) {
		User loggedinUser = userService.GetUserByPrincipal(principal);
		model.put("loggedinUser", loggedinUser);
		model.put("PageTitle", "Admin > Modify User");
		
		if(loggedinUser.getRole().getName().equals("ROLE_ADMIN")) {
			User foundUser = userRepository.findById(id).get();
			foundUser.setEnabled(!foundUser.isEnabled()); 
			foundUser.setMp(foundUser.getPassword());
			userRepository.save(foundUser);
			
			return "redirect:/admin/users";
		}
			
		return "error";
	}
	
	
	@RequestMapping(value="/admin/add-user", method=RequestMethod.GET)
	public String ShowAddNewUser(ModelMap model, Principal principal) {
		User loggedinUser = userService.GetUserByPrincipal(principal);
		
		
		System.err.println("ShowAddNewUser > loggedinUser: " + loggedinUser);
		
		if(loggedinUser.getRole().getName().equals("ROLE_ADMIN")) {
			model.put("loggedinUser", loggedinUser);
			model.put("PageTitle", "Admin > Add new user");
			model.put("user", new User());
			model.put("enabledValues", enabledValues());
			model.put("roleValues", roleValues());
			return "admin/add-user";
		}
			
		return "error";
	}

	@RequestMapping(value="/admin/add-user", method=RequestMethod.POST)
	public String AddNewUser(@Valid User user, Principal principal,
			ModelMap model, BindingResult result, Errors errors) 
					throws UserAlreadyExistException, EmailExistsException {
		User loggedinUser = userService.GetUserByPrincipal(principal);
		//model.put("loggedinUser", loggedinUser);
		
		System.err.println("ShowAddNewUser > loggedinUser: " + loggedinUser);
		System.err.println("ShowAddNewUser > user: " + user);
		
		if(loggedinUser.getRole().getName().equals("ROLE_ADMIN")) {
			ValidationUtils.invokeValidator(new UserValidator(), user, errors);
			
			if (!result.hasErrors()) {
				try {
		        	iUserService.registerNewUserAccount(user);
		        	return "redirect:/admin/users"; 
				} catch (UserAlreadyExistException | 
						EmailExistsException | 
						Exception e) {
					model.put("exception", e.getMessage());
					//model.put("loggedinUser", loggedinUser);
					//model.put("user", user);
					model.put("enabledValues", enabledValues());
					model.put("roleValues", roleValues());
					return "admin/add-user";
				}
		    } else {
		    	List<String> errorMessages = new ArrayList<String>();
		    	for (ObjectError obj : errors.getAllErrors()) {
		    		errorMessages.add(obj.getDefaultMessage());
				}
		    	
		    	model.put("errorMessages", errorMessages);
		    	//model.put("loggedinUser", loggedinUser);
		    	//model.put("user", user);
				model.put("enabledValues", enabledValues());
				model.put("roleValues", roleValues());
		    	return "admin/add-user";
		    }
		}
		
		
		return "error";
	}
	
	
	
	//==========Methods=============================
	
	private Map<String, String> roleValues() {
		Map<String,String> roleValues = new LinkedHashMap<String,String>();
		roleValues.put("ROLE_ADMIN", "Admin");
		roleValues.put("ROLE_USER", "User");
		return roleValues;
	}


	private Map<String, String> enabledValues() {
		Map<String,String> enabledValues = new LinkedHashMap<String,String>();
		enabledValues.put("true", "Activated: User can use the account immediately");
		enabledValues.put("false", "Suspended: User should verify its email to activate the account");
		return enabledValues;
	}
	
}
