package com.jalizadeh.springboot.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jalizadeh.springboot.web.model.User;

@Controller
public class LoginController {
	
	@RequestMapping(path = "/login", method = RequestMethod.GET)
    public String LoginForm(ModelMap model,
    		HttpServletRequest request) {
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
	

    @RequestMapping("/access_denied")
    public String accessDenied() {
        return "access_denied";
    }
}
