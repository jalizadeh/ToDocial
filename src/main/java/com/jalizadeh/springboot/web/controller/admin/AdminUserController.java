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
			userRepository.save(foundUser);
			
			return "redirect:/admin/users";
		}
			
		return "error";
	}
	
	
	@RequestMapping(value="/admin/add-user", method=RequestMethod.GET)
	public String ShowAddNewUser(ModelMap model, Principal principal) {
		User user = userService.GetUserByPrincipal(principal);
		model.put("user", user);
		model.put("PageTitle", "Admin > Add new user");
		
		if(user.getRole().getName().equals("ROLE_ADMIN")) {
			User nUser = new User();
			model.put("nUser", nUser);
			
			Map<String,String> enabledValues = new LinkedHashMap<String,String>();
			enabledValues.put("true", "Activated: User can use the account immediately");
			enabledValues.put("false", "Suspended: User should verify its email to activate the account");
			model.put("enabledValues", enabledValues);
			
			
			Map<String,String> roleValues = new LinkedHashMap<String,String>();
			roleValues.put("ROLE_ADMIN", "Admin");
			roleValues.put("ROLE_USER", "User");
			model.put("roleValues", roleValues);
			
			return "admin/add-user";
		}
			
		return "error";
	}
	
	
	@RequestMapping(value="/admin/add-user", method=RequestMethod.POST)
	public String AddNewUser(@Valid User nUser, Principal principal,
			ModelMap model, BindingResult result, Errors errors) 
					throws UserAlreadyExistException, EmailExistsException {
		User user = userService.GetUserByPrincipal(principal);
		model.put("user", user);
		User registered = null;
		
		if(user.getRole().getName().equals("ROLE_ADMIN")) {
			ValidationUtils.invokeValidator(new UserValidator(), user, errors);
			
			System.err.println(nUser);
			
			if (!result.hasErrors()) {
				try {
		        	registered = iUserService.registerNewUserAccount(user);
				} catch (Exception e) {
					model.put("errorMessages", new FlashMessage(e.getMessage(), FlashMessage.Status.FAILURE));
					return "admin/add-user";
				}
		    }
			
			if(result.hasErrors()) {
		    	List<String> errorMessages = new ArrayList<String>();
		    	for (ObjectError obj : errors.getAllErrors()) {
		    		errorMessages.add(obj.getDefaultMessage());
				}
		    	
		    	model.put("errorMessages", errorMessages);
		    	return "admin/add-user";
		    }
			
			if (registered == null)
		    	return "admin/add-user";
		}
		
		
		return "error";
	}
	
}
