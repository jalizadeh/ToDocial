package com.jalizadeh.springboot.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jalizadeh.springboot.web.model.Todo;
import com.jalizadeh.springboot.web.model.TodoLog;
import com.jalizadeh.springboot.web.model.User;
import com.jalizadeh.springboot.web.repository.TodoLogRepository;
import com.jalizadeh.springboot.web.repository.TodoRepository;
import com.jalizadeh.springboot.web.service.UserService;

@Controller
public class TodoController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private TodoRepository todoRepository;
	
	@Autowired
	private TodoLogRepository todoLogRepository;
	
	@InitBinder
	protected void InitBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, 
				new CustomDateEditor(dateFormat, false));
	}
	
	
	@RequestMapping(value = "/@{username}", method = RequestMethod.GET)
	public String ShowTodosList(ModelMap model, @PathVariable String username) {
		List<Todo> allTodos = todoRepository.findAll();
		model.put("todos", allTodos);
		model.put("todoCount", allTodos.size());
		model.put("PageTitle", "Todo Lists");
		return "list-todos";
	}
	
	
	@RequestMapping(value = "/@{username}", method = RequestMethod.POST)
	public String AddNewTodoLog(ModelMap model, 
			@RequestParam Long todoId, 
			@RequestParam String log) {

		User user = userService.GetAuthenticatedUser();

		//TODO: check for security+null
		
		TodoLog todoLog = new TodoLog(new Date(), log);
		Todo todo = todoRepository.findOneById(todoId);
		
		todoLog.getTodos().add(todo);
		todo.getLogs().add(todoLog);
		
		todoRepository.save(todo);
		
		return "redirect:/@" + user.getUsername();
	}
	
	
	@RequestMapping(value = "/delete-todo", method = RequestMethod.GET)
	public String DeleteTodo(ModelMap model, @RequestParam Long id) {
		User user = userService.GetAuthenticatedUser();
		todoRepository.deleteById(id);
		return "redirect:/@" + user.getUsername();
	}
	
	
	@RequestMapping(value = "/delete-todo-log", method = RequestMethod.GET)
	public String DeleteTodoLog(ModelMap model, @RequestParam Long id) {
		User user = userService.GetAuthenticatedUser();
		todoLogRepository.deleteById(id);
		return "redirect:/@" + user.getUsername();
	}
	
	
	
	@RequestMapping(value = "/add-todo", method = RequestMethod.GET)
	public String ShowAddTodo(ModelMap model) {
		model.put("PageTitle", "Add new Todo");
		model.addAttribute("todo",new Todo());
	
		Map<String,String> isDoneValues = new LinkedHashMap<String,String>();
		isDoneValues.put("true", "Yes");
		isDoneValues.put("false", "No");
		model.put("isDoneValues", isDoneValues);
			    
		return "add-todo";
	}
	
	@RequestMapping(value = "/add-todo", method = RequestMethod.POST)
	public String AddTodo(ModelMap model, @Valid Todo todo,
			BindingResult result) {
		
		User user = userService.GetAuthenticatedUser();
    	todo.setUser(user);
    	
		if(result.hasErrors()) {
			model.put("error", "Enter at least 10");
			return "add-todo";
		}
		
		todoRepository.save(todo);
		return "redirect:/@" + user.getUsername();
	}
	
	
	@RequestMapping(value = "/update-todo", method = RequestMethod.GET)
	public String ShowUpdateTodoPage(ModelMap model, @RequestParam Long id) {
		model.put("PageTitle", "Update Todo");
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
	public String UpdateTodo(ModelMap model, @Valid Todo todo,
			BindingResult result) {
		
		User user = userService.GetAuthenticatedUser();
    	
		if(result.hasErrors()) {
			model.put("error", "Enter at least 10");
			return "update-todo";
		}
		
		todo.setUser(user);
		todo.setLogs(todoRepository.findOneById(todo.getId()).getLogs());
		todoRepository.save(todo);
		
		return "redirect:/@" + user.getUsername();
	}
	
	
	@RequestMapping(value = "/todo-state", method = RequestMethod.GET)
	public String changeState(ModelMap model, @RequestParam Long id) {
		User user = userService.GetAuthenticatedUser();
		Todo todo = todoRepository.getOne(id);
		todo.setDone(!todo.isDone());
		todoRepository.save(todo);
		return "redirect:/@" + user.getUsername();
	}
	
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String SearchTodo(ModelMap model, 
			@RequestParam(defaultValue="") String q) {
		//model.put("loggedinUser", userService.GetAuthenticatedUser());
		model.put("PageTitle", "Search for: " + q);
		
		List<Todo> todos = new ArrayList<Todo>();
		for (Todo todo : todoRepository.findAll()) {
			if(todo.getDesc().toLowerCase().contains(q))
				todos.add(todo);
		}
		
		model.put("todos", todos);
		model.put("result", todos.size() + " results found for <mark>" + q + "</mark>");
		
		return "search";
	}
}
