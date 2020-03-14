package com.jalizadeh.springboot.web.controller;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jalizadeh.springboot.web.model.FlashMessage;
import com.jalizadeh.springboot.web.model.PasswordResetToken;
import com.jalizadeh.springboot.web.model.User;
import com.jalizadeh.springboot.web.registration.OnPasswordResetEvent;
import com.jalizadeh.springboot.web.repository.PasswordResetTokenRepository;
import com.jalizadeh.springboot.web.service.PasswordResetTokenService;
import com.jalizadeh.springboot.web.service.TokenService;
import com.jalizadeh.springboot.web.service.UserService;

@Controller
public class LoginLogoutController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenService tokenSrevice;
	
	@Autowired
	private PasswordResetTokenService prtService;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
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
		
		String tokenStatus = tokenSrevice.validateVerificationToken(
				tokenSrevice.TOKEN_TYPE_PASSWORD_RESET, token);
		
		switch (tokenStatus) {
			case "valid":
				User user = prtService.findUserByToken(token);
				
				//I need to access the user and change it's password later
				// the best way is to keep it in the Security Context
				Authentication auth = new UsernamePasswordAuthenticationToken(user,
						null,
						userDetailsService.loadUserByUsername(user.getUsername()).getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(auth);
				
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
			RedirectAttributes redirectAttributes) {
		model.put("PageTitle", "Reset Password");
		
		if (!password.equals(mp)) {
			model.put("errorMessages", "Passwords do not match");
            return "change-password";
        }
		
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);

		userService.changePassword(user, password);
		
		redirectAttributes.addFlashAttribute("message", 
				new FlashMessage("Your password is changed successfully.", FlashMessage.Status.success));
		return "redirect:/login";
	}
}
