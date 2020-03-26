package com.jalizadeh.springboot.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jalizadeh.springboot.web.model.FlashMessage;
import com.jalizadeh.springboot.web.model.Todo;
import com.jalizadeh.springboot.web.model.TodoLog;
import com.jalizadeh.springboot.web.model.User;
import com.jalizadeh.springboot.web.repository.TodoLogRepository;
import com.jalizadeh.springboot.web.repository.TodoRepository;
import com.jalizadeh.springboot.web.repository.UserRepository;
import com.jalizadeh.springboot.web.service.CommonServices;
import com.jalizadeh.springboot.web.service.UserService;

@Controller
public class TodoController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TodoRepository todoRepository;
	
	@Autowired
	private TodoLogRepository todoLogRepository;
	
	@Autowired
	private CommonServices utilites;
	
	@InitBinder
	protected void InitBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, 
				new CustomDateEditor(dateFormat, false));
	}

	
	/**
	 * If user is authenticated, he will be redirected to his personal page,
	 * but if it is an anonymous user, can access the public area of
	 * a registered user's page
	 */
	@RequestMapping(value = "/@{username}", method = RequestMethod.GET)
	public String ShowTodosList(ModelMap model, @PathVariable String username,
			RedirectAttributes redirectAttributes) {
		
		User currentUser = userService.GetAuthenticatedUser();
		User targetUser = userRepository.findByUsername(username);
		
		//if there is no mathcing account
		if (targetUser == null) {
			redirectAttributes.addFlashAttribute("exception", 
					"There is no account matching '" + username + "'");
			return "redirect:/error";
		}

		if (utilites.isUserAnonymous()) {
			model.put("user", targetUser); 
			model.put("todos", todoRepository.findAllByUserIdAndPubliccTrue(targetUser.getId()));
			String pageTitle = targetUser.getFirstname() + " " + targetUser.getLastname() + "(" + 
					targetUser.getUsername() + ")";
			model.put("PageTitle", pageTitle);
			return "public-page";
		}
		
		
		if(!currentUser.getUsername().equals(username)) {
			
			List<String> listOfFollowings = new ArrayList<>();
			for (User user : currentUser.getFollowings()) {
				listOfFollowings.add(user.getUsername());
			}
			
			for (String following : listOfFollowings) {
				if(following.equals(targetUser.getUsername())) {
					model.put("isfollowing", true);
					break;
				}else {
					model.put("isfollowing", false);
				}
			}
			
			model.put("user", targetUser); 
			model.put("todos", todoRepository.findAllByUserIdAndPubliccTrue(targetUser.getId()));
			String pageTitle = targetUser.getFirstname() + " " + targetUser.getLastname() + "(" + 
					targetUser.getUsername() + ")";
			model.put("PageTitle", pageTitle);
			return "public-page";
		}
		

		model.put("LoggedinUsers", userService.getAllLoggedinUsers());
		model.put("user", targetUser);
		model.put("todos", todoRepository.findAll());
		model.put("PageTitle", "My Todos");
		return "my-todos";
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
		return "todo";
	}
	
	@RequestMapping(value = "/add-todo", method = RequestMethod.POST)
	public String AddTodo(@Valid Todo todo, BindingResult result,
			RedirectAttributes redirectAttributes, ModelMap model) {
		
		if(result.hasErrors()) {
			model.put("error", "Enter at least 10");
			return "add-todo";
		}
		
		User user = userService.GetAuthenticatedUser();
    	todo.setUser(user);
    	todo.setCreation_date(new Date());
		todoRepository.save(todo);
		
		redirectAttributes.addFlashAttribute("flash", 
				new FlashMessage("Todo created successfully", FlashMessage.Status.success));
		return "redirect:/@" + user.getUsername();
	}
	
	
	@RequestMapping(value = "/update-todo", method = RequestMethod.GET)
	public String ShowUpdateTodoPage(ModelMap model, @RequestParam Long id) {
		model.put("PageTitle", "Update Todo");
		model.put("todo", todoRepository.getOne(id));
		return "todo";
	}
	
	@RequestMapping(value = "/update-todo", method = RequestMethod.POST)
	public String UpdateTodo(@Valid Todo todo, BindingResult result,
			RedirectAttributes redirectAttributes, ModelMap model) {
		
		if(result.hasErrors()) {
			model.put("error", "Enter at least 10");
			return "todo";
		}

		User user = userService.GetAuthenticatedUser();
		Todo ref = todoRepository.findOneById(todo.getId());
		
		todo.setUser(user);
		todo.setLike(ref.getLike());
		todo.setCreation_date(ref.getCreation_date());
		todo.setLogs(ref.getLogs());
		todoRepository.save(todo);
		
		redirectAttributes.addFlashAttribute("flash", 
				new FlashMessage("Todo updated successfully", FlashMessage.Status.success));
		return "redirect:/@" + user.getUsername();
	}
	
	
	@RequestMapping(value = "/todo-state", method = RequestMethod.GET)
	public String changeState(ModelMap model, @RequestParam Long id) {
		User user = userService.GetAuthenticatedUser();
		Todo todo = todoRepository.getOne(id);
		todo.setCompleted(!todo.isCompleted());
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
