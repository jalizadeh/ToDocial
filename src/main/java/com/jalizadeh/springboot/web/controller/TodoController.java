package com.jalizadeh.springboot.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jalizadeh.springboot.web.model.Todo;
import com.jalizadeh.springboot.web.service.TodoService;

@Controller
public class TodoController {
	
	@Autowired
	TodoService service;
	
	@InitBinder
	protected void InitBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, 
				new CustomDateEditor(dateFormat, false));
	}
	
	
	@RequestMapping(value = "/list-todos", method = RequestMethod.GET)
	public String ShowTodosList(ModelMap model) {
		String name = GetLoggedInUserName();
		
		model.put("todos", service.retrieveTodos(name));
		model.put("name", GetLoggedInUserName());
		return "list-todos";
	}


	private String GetLoggedInUserName() {
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		
		if(principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
			
		}
		
		return principal.toString();
	}
	
	
	
	
	@RequestMapping(value = "/delete-todo", method = RequestMethod.GET)
	public String DeleteTodo(ModelMap model, @RequestParam int id) {
		service.deleteTodo(id);
		return "redirect:/list-todos";
	}
	
	
	
	@RequestMapping(value = "/add-todo", method = RequestMethod.GET)
	public String ShowAddTodo(ModelMap model) {
		model.addAttribute("todo",new Todo(0, GetLoggedInUserName(),
				"Default", new Date(), false));
		return "add-todo";
	}
	
	@RequestMapping(value = "/add-todo", method = RequestMethod.POST)
	public String AddTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
		if(result.hasErrors()) {
			model.put("error", "Enter at least 10");
			return "add-todo";
		}
		
		service.addTodo(GetLoggedInUserName(), 
				todo.getDesc(), todo.getTargetDate(), false);
		return "redirect:/list-todos";
	}
	
	@RequestMapping(value = "/update-todo", method = RequestMethod.GET)
	public String ShowUpdateTodoPage(ModelMap model, @RequestParam int id) {
		Todo todo = service.getTodoById(id);
		model.put("todo", todo);
		return "update-todo";
	}
	
	@RequestMapping(value = "/update-todo", method = RequestMethod.POST)
	public String UpdateTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
		if(result.hasErrors()) {
			model.put("error", "Enter at least 10");
			return "update-todo";
		}
		
		todo.setUser(GetLoggedInUserName());
		service.updateTodo(todo);
		return "redirect:/list-todos";
	}
}
