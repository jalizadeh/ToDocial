package com.jalizadeh.springboot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.jalizadeh.springboot.web.service.TodoService;

@Controller
@SessionAttributes("name")
public class TodoController {
	
	@Autowired
	TodoService service;
	
	@RequestMapping(value = "/list-todos", method = RequestMethod.GET)
	public String ShowTodosList(ModelMap model) {
		String name = (String) model.get("name");
		
		model.put("todos", service.retrieveTodos(name));
		return "list-todos";
	}
	
	
}
