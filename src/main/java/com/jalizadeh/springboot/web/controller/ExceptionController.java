package com.jalizadeh.springboot.web.controller;

import java.io.IOException;

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
    		HttpServletResponse response) throws IOException {
    	
    	Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        ModelAndView mv = new ModelAndView();
        
        if(status != null) {
        	Integer statusCode = Integer.valueOf(status.toString());
            
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
            	mv.addObject("exception", "404 : NOT FOUND<br/>The web page is not found");
            	mv.addObject("PageTitle", "404 : NOT FOUND");
            	//mv.addObject("loggedinUser", userService.GetAuthenticatedUser());
            } else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            	mv.addObject("exception", "500 : INTERNAL SERVER ERROR<br/>There is a problem on server, please try again");
            	mv.addObject("PageTitle", "500 : INTERNAL SERVER ERROR");
            	//mv.addObject("loggedinUser", userService.GetAuthenticatedUser());
            } else if(statusCode == HttpStatus.FORBIDDEN.value()) {
            	mv.addObject("exception", "403 : FORBIDDEN<br/>You are not authorized to access.");
            	mv.addObject("PageTitle", "403 : FORBIDDEN");
            	//mv.addObject("loggedinUser", userService.GetAuthenticatedUser());
            } else if(statusCode == HttpStatus.BAD_REQUEST.value()) {
            	mv.addObject("exception", "400 : BAD REQUEST<br/>Your request is not valid. It seems you entered wrong information that we can't understand.");
            	mv.addObject("PageTitle", "400 : BAD REQUEST");
            	//mv.addObject("loggedinUser", userService.GetAuthenticatedUser());
            } else {
            	mv.addObject("exception", statusCode + " : " + status.toString() + "<br/>That's all we know.");
            	mv.addObject("PageTitle", statusCode + " : " + status.toString());
            	//mv.addObject("loggedinUser", userService.GetAuthenticatedUser());
            }
        }
        
    	mv.setViewName("error");
    	return mv;
    }
}
