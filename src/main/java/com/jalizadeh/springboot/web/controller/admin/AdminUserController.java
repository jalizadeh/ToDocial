package com.jalizadeh.springboot.web.controller.admin;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import com.jalizadeh.springboot.web.error.EmailExistsException;
import com.jalizadeh.springboot.web.error.UserAlreadyExistException;
import com.jalizadeh.springboot.web.model.User;
import com.jalizadeh.springboot.web.registration.OnRegistrationCompleteEvent;
import com.jalizadeh.springboot.web.repository.RoleRepository;
import com.jalizadeh.springboot.web.repository.TodoRepository;
import com.jalizadeh.springboot.web.repository.UserRepository;
import com.jalizadeh.springboot.web.repository.VerificationTokenRepository;
import com.jalizadeh.springboot.web.service.UserService;
import com.jalizadeh.springboot.web.validator.UserValidator;

@Controller
public class AdminUserController {

	
	@Autowired
	UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private TodoRepository todoRepository;
	
	@Autowired
	private VerificationTokenRepository tokenRepository;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	@RequestMapping(value="/admin/users", method=RequestMethod.GET)
	public String ShowAdminPanel_Users(ModelMap model) {
		model.put("PageTitle", "Admin > Users");

		model.put("listOfLoggedinUsers", userService.getAllLoggedinUsersAsString());
		model.put("all_users", userRepository.findAll());
		model.put("users_count", userRepository.count());
		return "admin/users";
	}
	
	
	@RequestMapping(value="/admin/change_user_state", method=RequestMethod.GET)
	public String ActivateUser(ModelMap model , @RequestParam Long id) {
		model.put("PageTitle", "Admin > Modify User");

		User foundUser = userRepository.findById(id).get();
		foundUser.setEnabled(!foundUser.isEnabled()); 
		foundUser.setMp(foundUser.getPassword());
		userRepository.save(foundUser);

		return "redirect:/admin/users";
	}
	
	
	@RequestMapping(value="/admin/add-user", method=RequestMethod.GET)
	public String ShowAddNewUser(ModelMap model) {
		model.put("PageTitle", "Admin > Add new user");
		model.put("user", new User());
		model.put("enabledValues", enabledValues());
		model.put("roleValues", roleValues());
		return "admin/add-user";
	}

	@RequestMapping(value="/admin/add-user", method=RequestMethod.POST)
	public String AddNewUser(@Valid User user, BindingResult result,
			Errors errors, ModelMap model,  WebRequest request) 
			throws UserAlreadyExistException, EmailExistsException {
		
		ValidationUtils.invokeValidator(new UserValidator(), user, errors);

		if (result.hasErrors()) {
			List<String> errorMessages = new ArrayList<String>();
			for (ObjectError obj : errors.getAllErrors()) {
				errorMessages.add(obj.getDefaultMessage());
			}

			model.put("errorMessages", errorMessages);
			model.put("user", user);
			model.put("enabledValues", enabledValues());
			model.put("roleValues", roleValues());
			return "admin/add-user";
		} else {
			try {
				User registered = null;

				//register and send verification email, if...
				if(user.isEnabled()) {
					registered = userService.registerNewUserAccount(user);
				} else {
					registered = userService.registerNewUserAccount(user);
					String appUrl = request.getContextPath();
					eventPublisher.publishEvent(new OnRegistrationCompleteEvent
							(registered, appUrl, request.getLocale()));	
				}

				return "redirect:/admin/users"; 

			} catch (UserAlreadyExistException | 
					EmailExistsException | 
					Exception e) {
				model.put("exception", e.getMessage());
				model.put("user", user);
				model.put("enabledValues", enabledValues());
				model.put("roleValues", roleValues());
				return "admin/add-user";
			}
		}
	}
	
	
	@RequestMapping(value="/admin/delete_user", method=RequestMethod.GET)
	public String deleteUser(ModelMap model , @RequestParam Long id) {
		
		if(todoRepository.findAllByUserId(id).size() > 0)
			todoRepository.deleteById(id);

		if(tokenRepository.findByUserId(id) != null)
			tokenRepository.deleteById(id);

		userRepository.deleteById(id);

		return "redirect:/admin/users";
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
