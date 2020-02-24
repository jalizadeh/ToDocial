package com.jalizadeh.springboot.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.jalizadeh.springboot.web.model.Todo;
import com.jalizadeh.springboot.web.service.TodoService;

@Controller
@SessionAttributes("name")
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
		String name = getLoggedInUserName(model);
		
		model.put("todos", service.retrieveTodos(name));
		return "list-todos";
	}


	private String getLoggedInUserName(ModelMap model) {
		return (String) model.get("name");
	}
	
	
	@RequestMapping(value = "/delete-todo", method = RequestMethod.GET)
	public String DeleteTodo(ModelMap model, @RequestParam int id) {
		service.deleteTodo(id);
		return "redirect:/list-todos";
	}
	
	
	
	@RequestMapping(value = "/add-todo", method = RequestMethod.GET)
	public String ShowAddTodo(ModelMap model) {
		model.addAttribute("todo",new Todo(0, getLoggedInUserName(model),
				"Default", new Date(), false));
		return "add-todo";
	}
	
	@RequestMapping(value = "/add-todo", method = RequestMethod.POST)
	public String AddTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
		if(result.hasErrors()) {
			model.put("error", "Enter at least 10");
			return "add-todo";
		}
		
		service.addTodo(getLoggedInUserName(model), 
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
		
		todo.setUser(getLoggedInUserName(model));
		service.updateTodo(todo);
		return "redirect:/list-todos";
	}
}
