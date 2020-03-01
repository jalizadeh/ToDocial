package com.jalizadeh.springboot.web.controller;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jalizadeh.springboot.web.service.UserService;

@Controller 
//@ControllerAdvice
public class ExceptionController implements ErrorController {
    private static final String ERROR_PATH = "/error";

    @Autowired
	UserService userService;
    
    /**
     * implement ErrorController to handle 404
     */
    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
    

    @RequestMapping(value = ERROR_PATH)
    public ModelAndView handleError(HttpServletRequest request, 
    		HttpServletResponse response,
    		Principal principal) throws IOException {
    	Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        ModelAndView mv = new ModelAndView();
        
        if(status != null) {
        	Integer statusCode = Integer.valueOf(status.toString());
            
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
            	mv.addObject("exception", "404 : The web page is not found");
            	mv.addObject("user", userService.GetUserByPrincipal(principal));
            } else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            	mv.addObject("exception", "500 : There is a problem on server, please try again");
            	mv.addObject("user", userService.GetUserByPrincipal(principal));
            } else if(statusCode == HttpStatus.FORBIDDEN.value()) {
            	mv.addObject("exception", "403 : Your request is not valid.<br/>The server understood the request but refuses to authorize it.");
            	mv.addObject("user", userService.GetUserByPrincipal(principal));
            } else {
            	mv.addObject("exception", statusCode + " : That's all we know.");
            	mv.addObject("user", userService.GetUserByPrincipal(principal));
            }
        }
        
    	mv.setViewName("error");
    	return mv;
    }
}
