package com.jalizadeh.springboot.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jalizadeh.springboot.web.error.EmailExistsException;
import com.jalizadeh.springboot.web.error.UserAlreadyExistException;
import com.jalizadeh.springboot.web.model.FlashMessage;
import com.jalizadeh.springboot.web.model.PasswordResetToken;
import com.jalizadeh.springboot.web.model.SecurityQuestion;
import com.jalizadeh.springboot.web.model.SecurityQuestionDefinition;
import com.jalizadeh.springboot.web.model.User;
import com.jalizadeh.springboot.web.registration.OnPasswordResetEvent;
import com.jalizadeh.springboot.web.registration.OnRegistrationCompleteEvent;
import com.jalizadeh.springboot.web.repository.SecurityQuestionDefinitionRepository;
import com.jalizadeh.springboot.web.repository.SecurityQuestionRepository;
import com.jalizadeh.springboot.web.service.IUserService;
import com.jalizadeh.springboot.web.service.PasswordResetTokenService;
import com.jalizadeh.springboot.web.service.TokenService;
import com.jalizadeh.springboot.web.service.UserService;
import com.jalizadeh.springboot.web.validator.UserValidator;

@Controller
public class LoginSignupController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private IUserService iUserService;
	
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
	ApplicationEventPublisher eventPublisher;
	
	
	@RequestMapping("/login")
    public String LoginForm(ModelMap model, HttpServletRequest request) {
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
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
		
		return "welcome";
    }
	
	
	
	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request,
			HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		
		redirectAttributes.addFlashAttribute("message",
				new FlashMessage("You logged out successfully.", FlashMessage.Status.success));
		return new ModelAndView("redirect:/login");
	}
	
	
	@RequestMapping(value="/forgot-password", method=RequestMethod.GET)
	public String forgotPasswordShowPage(ModelMap model) {
		model.put("PageTitle", "Forgot Password");
		return "forgot-password";
	}
	
	
	@RequestMapping(value="/forgot-password", method=RequestMethod.POST)
	public String forgotPasswordSubmit(ModelMap model, 
			@RequestParam String email, WebRequest request,
			RedirectAttributes redirectAttributes) {
		model.put("PageTitle", "Forgot Password");
		User user = userService.findByEmail(email);
		
		if(user != null) {
			String token = UUID.randomUUID().toString();
			PasswordResetToken prt = new PasswordResetToken(token, user, new Date());
			prtService.saveNewToken(prt);

    		String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnPasswordResetEvent
            		(prt, appUrl, request.getLocale()));
			
            redirectAttributes.addFlashAttribute("flash", 
            		new FlashMessage("An email is sent to you", FlashMessage.Status.success));
		} else {
			redirectAttributes.addFlashAttribute("flash", 
					new FlashMessage("The email is not registered", FlashMessage.Status.danger));	
		}
		
		return "redirect:/forgot-password";
	}
	
	
	@RequestMapping(value="reset-password", method=RequestMethod.GET)
	public String resetPasswordConfirmedShowPage(@RequestParam String token,
			ModelMap model, RedirectAttributes redirectAttributes) {
		model.put("PageTitle", "Reset Password");
		
		String tokenStatus = tokenService.validateVerificationToken(
				tokenService.TOKEN_TYPE_PASSWORD_RESET,
				token); 

		switch (tokenStatus) {
			case "valid":
				User user = prtService.findUserByToken(token);
				
				//I need to access the user and change it's password later
				// the best way is to keep it in the Security Context
				Authentication auth = new UsernamePasswordAuthenticationToken(user,
						null,
						userDetailsService.loadUserByUsername(user.getUsername()).getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(auth);
				model.put("securityQuestions", sqdRepo.findAll());
				prtService.deleteByToken(token);
				return "change-password";
				
			case "invalid":
				redirectAttributes.addFlashAttribute("flash", 
						new FlashMessage("There is something wrong with your token. Please try again.", 
								FlashMessage.Status.danger));
				return "redirect:/forgot-password";
				
			case "expired":
				redirectAttributes.addFlashAttribute("flash", 
						new FlashMessage("Your token is expired. Please try again.", 
								FlashMessage.Status.danger));
				return "redirect:/forgot-password";
	
			default:
				break;
		}
		
		return "login";
	}
	
	
	
	@RequestMapping(value="change-password", method=RequestMethod.POST)
	public String changePassword(ModelMap model,
			@RequestParam String password, @RequestParam String mp,
			@RequestParam Long sq, @RequestParam String sqa,
			RedirectAttributes redirectAttributes) {
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
		if(!b.matches(sqa, secQ.getAnswer()) || 
				sq != secQ.getQuestionDefinition().getId()) {
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
	
	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String SignupMessage(ModelMap model) {
		model.addAttribute("user", new User());
		model.put("PageTitle", "Sign up");
		model.put("securityQuestions", sqdRepo.findAll());
		return "signup";
	}
	
	
	//Register new user and handle all errors
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ModelAndView registerUserAccount (@Valid User user,
			@RequestParam Long sq, @RequestParam String sqa, 
			BindingResult result, Errors errors, WebRequest request) 
	    	throws UserAlreadyExistException, EmailExistsException { 
		
		System.err.println("1");
		//ValidationUtils.invokeValidator(new UserValidator(), user, errors);
	    
		if(result.hasErrors()) {
			List<String> errorMessages = new ArrayList<String>();
	    	for (ObjectError obj : errors.getAllErrors()) {
	    		errorMessages.add(obj.getDefaultMessage());
			}
	    	ModelAndView mv = new ModelAndView();
	    	mv.addObject("errorMessages", errorMessages);
	    	mv.addObject("user", user);
	    	mv.setViewName("signup");
	    	System.err.println("2");
	    	//return new ModelAndView("signup", "errorMessages", errorMessages);
	    	return mv;
		}
		
		User registered = null;
		System.err.println("3");
    	try {
    		registered = iUserService.registerNewUserAccount(user);
    		SecurityQuestionDefinition sqd = sqdRepo.getOne(sq);
    		sqRepo.save(new SecurityQuestion(registered,
    				sqd, 
    				passwordEncoder.encode(sqa)));
    		
    		//The process of creating token and sending email is asynchronous
    		// and is handled by eventPublisher
    		String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent
            		(registered, appUrl, request.getLocale()));
            System.err.println("4");
    	} catch (UserAlreadyExistException | EmailExistsException | 
				Exception e) {
			
    		ModelAndView mv = new ModelAndView();
	    	mv.addObject("exception", e.getMessage());
	    	mv.addObject("user", user);
	    	mv.setViewName("signup");
	    	System.err.println("51");
	    	return mv;
    		//return new ModelAndView("signup", "exception", e.getMessage());
		}
    	System.err.println("6");
    	
    	return new ModelAndView("confirm_email", "registeredUser", registered);
	}
	
	
	@RequestMapping(value="/registration-confirm", method=RequestMethod.GET)
	public String confirmRegistration(ModelMap model, WebRequest request,
			@RequestParam("token") String token) {
		
		Locale locale = request.getLocale();
		model.put("user", new User());
		
		String tokenStatus = tokenService.validateVerificationToken(
				tokenService.TOKEN_TYPE_VERIFICATION,token);
		
		switch (tokenStatus) {
			case "valid":
				model.put("flash", 
						new FlashMessage("Your email is verified successfully.\nYou can login now.", 
								FlashMessage.Status.success));
				break;
			case "invalid":
				model.put("flash", 
						new FlashMessage("There is something wrong with your token.<br/>Please login and follow the instructions.", 
								FlashMessage.Status.danger));
				break;
			case "expired":
				model.put("flash", 
						new FlashMessage("Your email verification token is expired.<br/>Please login and follow the instructions.", 
								FlashMessage.Status.danger));
				break;
	
			default:
				break;
		}
		
		return "login";
	}
}
