package com.jalizadeh.springboot.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

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

import com.jalizadeh.springboot.web.model.Todo;
import com.jalizadeh.springboot.web.repository.TodoRepository;
import com.jalizadeh.springboot.web.service.UserService;

@Controller
public class TodoController {

	@Autowired
	UserService userService;
	
	@Autowired
	TodoRepository todoRepository;
	
	@InitBinder
	protected void InitBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, 
				new CustomDateEditor(dateFormat, false));
	}
	
	
	@RequestMapping(value = "/list-todos", method = RequestMethod.GET)
	public String ShowTodosList(ModelMap model) {
		model.put("todos", todoRepository.findAll());
		model.put("todo_count", todoRepository.count());
		model.put("name", userService.GetLoggedinUsername());
		return "list-todos";
	}
	
	
	@RequestMapping(value = "/delete-todo", method = RequestMethod.GET)
	public String DeleteTodo(ModelMap model, @RequestParam Long id) {
		todoRepository.deleteById(id);
		return "redirect:/list-todos";
	}
	
	
	
	@RequestMapping(value = "/add-todo", method = RequestMethod.GET)
	public String ShowAddTodo(ModelMap model) {
		model.put("name", userService.GetLoggedinUsername());
		model.addAttribute("todo",new Todo());
	
		Map<String,String> isDoneValues = new LinkedHashMap<String,String>();
		isDoneValues.put("true", "Yes");
		isDoneValues.put("false", "No");
		model.put("isDoneValues", isDoneValues);
			    
			    
		return "add-todo";
	}
	
	@RequestMapping(value = "/add-todo", method = RequestMethod.POST)
	public String AddTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
		if(result.hasErrors()) {
			model.put("error", "Enter at least 10");
			return "add-todo";
		}
		
		todoRepository.save(new Todo(userService.GetLoggedinUsername(), 
				todo.getDesc(), todo.getTargetDate(), todo.isDone()));
		return "redirect:/list-todos";
	}
	
	
	@RequestMapping(value = "/update-todo", method = RequestMethod.GET)
	public String ShowUpdateTodoPage(ModelMap model, @RequestParam Long id) {
		model.put("name", userService.GetLoggedinUsername());
		
		Todo todo = todoRepository.getOne(id);
		model.put("todo", todo);
		
		//the value will be auto-selected by spring
		Map<String,String> isDoneValues = new LinkedHashMap<String,String>();
		isDoneValues.put("true", "Yes");
		isDoneValues.put("false", "No");
	    model.put("isDoneValues", isDoneValues);
	    
		return "update-todo";
	}
	
	@RequestMapping(value = "/update-todo", method = RequestMethod.POST)
	public String UpdateTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
		if(result.hasErrors()) {
			model.put("error", "Enter at least 10");
			return "update-todo";
		}
		
		todo.setUser(userService.GetLoggedinUsername());
		todoRepository.save(todo);
		
		return "redirect:/list-todos";
	}
}
