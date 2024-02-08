package com.jalizadeh.todocial.controller;

import com.jalizadeh.todocial.model.settings.SettingsGeneralConfig;
import com.jalizadeh.todocial.exception.EmailExistsException;
import com.jalizadeh.todocial.exception.UserAlreadyExistException;
import com.jalizadeh.todocial.model.*;
import com.jalizadeh.todocial.model.user.PasswordResetToken;
import com.jalizadeh.todocial.model.user.SecurityQuestion;
import com.jalizadeh.todocial.model.user.SecurityQuestionDefinition;
import com.jalizadeh.todocial.model.user.User;
import com.jalizadeh.todocial.repository.user.SecurityQuestionDefinitionRepository;
import com.jalizadeh.todocial.repository.user.SecurityQuestionRepository;
import com.jalizadeh.todocial.service.*;
import com.jalizadeh.todocial.service.registration.OnPasswordResetEvent;
import com.jalizadeh.todocial.service.registration.OnRegistrationCompleteEvent;
import com.jalizadeh.todocial.service.storage.StorageFileSystemService;
import com.jalizadeh.todocial.utils.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;

@Controller
public class LoginSignupController{

	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenService tokenService;

	@Autowired
	private SecurityQuestionDefinitionRepository sqdRepo;
	
	@Autowired
	private SecurityQuestionRepository sqRepo;
	
	@Autowired
	private PasswordResetTokenService prtService;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	private CommonServices utilites;
	
	@Autowired
	private SettingsGeneralConfig settings;
	
	//image upload
	private final StorageFileSystemService storageService;

	@Autowired
	public LoginSignupController(StorageFileSystemService storageService) {
		this.storageService = storageService;
	}
	
	
	@GetMapping("/login")
    public String showLoginPage(ModelMap model, HttpServletRequest request) {
		if(utilites.isUserAnonymous()) {
			model.put("settings", settings);
			model.put("PageTitle", "Log in");
	        model.put("user", new User());
	        
	        try {
	            Object flash = request.getSession().getAttribute("flash");
	            model.put("flash", flash);
	            request.getSession().removeAttribute("flash");
	        } catch (Exception ex) {
	            // "flash" session attribute must not exist...do nothing and proceed normally
	        }
	        
	        return "login";
		}
		
		return "redirect:/";
    }
	
	
	
	@GetMapping("/logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
			SecurityContextHolder.clearContext();
		}

		Log.info("User: " + auth.getName() + " signed out successfully");
		
		redirectAttributes.addFlashAttribute("message",
				new FlashMessage("You logged out successfully.", FlashMessage.Status.success));
		return new ModelAndView("redirect:/login");
	}
	
	
	@GetMapping("/forgot-password")
	public String showForgotPasswordPage(ModelMap model) {
		model.put("settings", settings);
		model.put("PageTitle", "Forgot Password");
		return "forgot-password";
	}
	
	
	@PostMapping("/forgot-password")
	public String forgotPasswordSubmit(ModelMap model, @RequestParam String email, WebRequest request, RedirectAttributes redirectAttributes) {
		//model.put("PageTitle", "Forgot Password");
		User user = userService.findByEmail(email);
		
		if(user != null) {
			String token = UUID.randomUUID().toString();
			PasswordResetToken prt = new PasswordResetToken(token, user, new Date());
			prtService.saveNewToken(prt);

    		String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnPasswordResetEvent(prt, appUrl, request.getLocale()));

			Log.info("User: " + user.getUsername() + " successfully requested for forgot password process");

            redirectAttributes.addFlashAttribute("flash", 
            		new FlashMessage("An email is sent to you", FlashMessage.Status.success));
		} else {
			Log.error("Forgot password process for email " + email + " failed. The email does not exist.");
			redirectAttributes.addFlashAttribute("flash", 
					new FlashMessage("The email is not registered", FlashMessage.Status.danger));	
		}
		
		return "redirect:/forgot-password";
	}
	
	
	@GetMapping("reset-password")
	public String showResetPasswordConfirmed(@RequestParam String token, ModelMap model, RedirectAttributes redirectAttributes) {
		model.put("settings", settings);
		model.put("PageTitle", "Reset Password");
		
		String tokenStatus = tokenService.validateToken(tokenService.TOKEN_TYPE_PASSWORD_RESET, token);

		switch (tokenStatus) {
			case "valid":
				User user = prtService.findUserByToken(token);
				
				//I need to access the user and change it's password later
				// the best way is to keep it in the Security Context
				Authentication auth = new UsernamePasswordAuthenticationToken(user,null,
						userDetailsService.loadUserByUsername(user.getUsername()).getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(auth);
				model.put("securityQuestions", sqdRepo.findAll());
				prtService.deleteByToken(token);
				return "change-password";
				
			case "invalid":
				redirectAttributes.addFlashAttribute("flash", 
						new FlashMessage("There is something wrong with your token. Please try again.", FlashMessage.Status.danger));
				return "redirect:/forgot-password";
				
			case "expired":
				redirectAttributes.addFlashAttribute("flash", 
						new FlashMessage("Your token is expired. Please try again.", FlashMessage.Status.danger));
				return "redirect:/forgot-password";
	
			default:
				break;
		}
		
		return "login";
	}
	
	
	
	@PostMapping("change-password")
	public String changePassword(ModelMap model, RedirectAttributes redirectAttributes,
			@RequestParam String password, @RequestParam String mp, @RequestParam Long sq, @RequestParam String sqa) {
		model.put("settings", settings);
		model.put("PageTitle", "Change Password");
		
		if (!password.equals(mp)) {
			model.put("errorMessages", "Passwords do not match");
			model.put("securityQuestions", sqdRepo.findAll());
            return "change-password";
        }
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		SecurityQuestion secQ = sqRepo.findByUserId(user.getId());
		
		//check answer validity
		BCryptPasswordEncoder b = new BCryptPasswordEncoder();
		if(!b.matches(sqa, secQ.getAnswer()) || !sq.equals(secQ.getQuestionDefinition().getId())) {
			model.put("errorMessages", "The given answer is not correct. Try again.");
			model.put("securityQuestions", sqdRepo.findAll());
            return "change-password";
		}
		
		//before going back to `login`, remove authentication
		SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);

		userService.changePassword(user, password);
		
		redirectAttributes.addFlashAttribute("message", 
				new FlashMessage("Your password is changed successfully.", FlashMessage.Status.success));
		return "redirect:/login";
	}
	
	@GetMapping("/signup")
	public String showSignupPage(ModelMap model) {
		if(utilites.isUserAnonymous()) {
			model.put("settings", settings);
			model.put("PageTitle", "Sign up");
			model.addAttribute("user", new User());
			model.put("securityQuestions", sqdRepo.findAll());
			return "signup";
		}
		
		return "redirect:/";
	}
	
	
	//Register new user and handle all errors
	@PostMapping("/signup")
	public ModelAndView registerUserAccount (@Valid User user, BindingResult result, Errors errors, WebRequest request,
			@RequestParam Long sq, @RequestParam String sqa, @RequestParam(value="file", required=false) MultipartFile file)
	    	throws UserAlreadyExistException, EmailExistsException { 
		
		//ValidationUtils.invokeValidator(new UserValidator(), user, errors);
		ModelAndView mv = new ModelAndView();
		mv.addObject("settings", settings);
	    
		if(result.hasErrors()) {
			List<String> errorMessages = new ArrayList<String>();
	    	for (ObjectError obj : errors.getAllErrors()) {
	    		errorMessages.add(obj.getDefaultMessage());
			}
	    	
	    	mv.addObject("errorMessages", errorMessages);
	    	mv.addObject("user", user);
	    	mv.addObject("securityQuestions", sqdRepo.findAll());
	    	mv.setViewName("signup");
	    	return mv;
		}
		
		User registered = null;
    	try {
    		if (file.isEmpty()) {
    			user.setPhoto("default.jpg");
			} else {
				storageService.store(file, ServiceTypes.todo, user.getUsername() + ".jpg");
				user.setPhoto(user.getUsername() + ".jpg");    			
			}
            
    		registered = userService.registerNewUserAccount(user);
    		SecurityQuestionDefinition sqd = sqdRepo.getOne(sq);
    		sqRepo.save(new SecurityQuestion(registered, sqd, passwordEncoder.encode(sqa)));
    		
    		//The process of creating token and sending email is asynchronous
    		// and is handled by eventPublisher
    		String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, appUrl, request.getLocale()));
    	} catch (UserAlreadyExistException | EmailExistsException | Exception e) {
	    	mv.addObject("exception", e.getMessage());
	    	mv.addObject("user", user);
	    	mv.addObject("securityQuestions", sqdRepo.findAll());
	    	mv.setViewName("signup");
	    	return mv;
		}
    	
    	return new ModelAndView("confirm_email", "registeredUser", registered);
	}
	
	
	@GetMapping("/registration-confirm")
	public String confirmRegistration(ModelMap model, WebRequest request, @RequestParam("token") String token) {
		Locale locale = request.getLocale();
		model.put("user", new User());
		model.put("settings", settings);
		
		String tokenStatus = tokenService.validateToken(tokenService.TOKEN_TYPE_VERIFICATION,token);
		
		switch (tokenStatus) {
			case "valid":
				model.put("flash", 
						new FlashMessage("Your email is verified successfully.\nYou can login now.", FlashMessage.Status.success));
				break;
			case "invalid":
				model.put("flash", 
						new FlashMessage("There is something wrong with your token.<br/>Please login and follow the instructions.", FlashMessage.Status.danger));
				break;
			case "expired":
				model.put("flash", 
						new FlashMessage("Your email verification token is expired.<br/>Please login and follow the instructions.", FlashMessage.Status.danger));
				break;
	
			default:
				break;
		}
		
		return "login";
	}
}
