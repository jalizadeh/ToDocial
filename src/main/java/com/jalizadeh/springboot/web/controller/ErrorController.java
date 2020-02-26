package com.jalizadeh.springboot.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.jalizadeh.springboot.web.service.UserService;

@Controller("error")
public class ErrorController {
	
	@Autowired
	UserService userService;
	
	@ExceptionHandler(Exception.class)
	public ModelAndView HandleException
		(HttpServletRequest request, HttpServletResponse response,
				Exception ex, ModelMap model) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("exception", ex.getStackTrace());
		mv.addObject("url", request.getRequestURL());
		
		model.put("url", request.getRequestURL());
		model.put("name", userService.GetLoggedinUsername());
		
		mv.setViewName("error");
		return mv;
	}
}
