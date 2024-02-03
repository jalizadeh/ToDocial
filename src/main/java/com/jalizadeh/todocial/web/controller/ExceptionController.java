package com.jalizadeh.todocial.web.controller;

import com.jalizadeh.todocial.web.controller.admin.model.SettingsGeneralConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller 
public class ExceptionController implements ErrorController {
    private static final String ERROR_PATH = "/error";

    @Autowired
	private SettingsGeneralConfig settings;
    
    @GetMapping(ERROR_PATH)
    public ModelAndView handleError(HttpServletRequest request,
                                    HttpServletResponse response) throws IOException {
    	
    	Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        ModelAndView mv = new ModelAndView();
        
        if(status != null) {
        	Integer statusCode = Integer.valueOf(status.toString());
        	mv.addObject("PageTitle", statusCode + " : " + status.toString());
        	
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
            	mv.addObject("exception", 
            			"404 : NOT FOUND<br/>The web page is not found");
            } else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            	mv.addObject("exception", 
            			"500 : INTERNAL SERVER ERROR<br/>There is a problem on server, please try again");
            } else if(statusCode == HttpStatus.FORBIDDEN.value()) {
            	mv.addObject("exception", 
            			"403 : FORBIDDEN<br/>You are not authorized to access.");
            } else if(statusCode == HttpStatus.BAD_REQUEST.value()) {
            	mv.addObject("exception", 
            			"400 : BAD REQUEST<br/>Your request is not valid. It seems you entered wrong information that we can't understand.");
            } else {
            	mv.addObject("exception", 
            			statusCode + " : " + status.toString() + "<br/>That's all we know.");
            }
        }
        
        mv.addObject("settings", settings);
        mv.addObject("PageTitle", "Error");
    	mv.setViewName("error");
    	return mv;
    }
    
}
