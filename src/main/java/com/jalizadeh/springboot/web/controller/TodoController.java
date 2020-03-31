package com.jalizadeh.springboot.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jalizadeh.springboot.web.controller.admin.model.SettingsGeneralConfig;
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
	
	@Autowired
	private SettingsGeneralConfig settings;
	
	
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
		model.put("settings", settings);
		
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
		model.put("todos", todoRepository.findAllByLoggedinUser());
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
	
	
	@GetMapping("/delete-todo")
	public String DeleteTodo(ModelMap model, @RequestParam Long id) {
		User user = userService.GetAuthenticatedUser();
		todoRepository.deleteById(id);
		return "redirect:/@" + user.getUsername();
	}
	
	
	@GetMapping("/delete-todo-log")
	public String DeleteTodoLog(ModelMap model, @RequestParam Long id) {
		User user = userService.GetAuthenticatedUser();
		todoLogRepository.deleteById(id);
		return "redirect:/@" + user.getUsername();
	}
	
	
	
	@GetMapping("/add-todo")
	public String ShowAddTodo(ModelMap model) {
		model.put("PageTitle", "Add new Todo");
		model.put("settings", settings);
		model.addAttribute("todo",new Todo());
		return "todo";
	}
	
	@PostMapping("/add-todo")
	public String AddTodo(@Valid Todo todo, BindingResult result,
			RedirectAttributes redirectAttributes, ModelMap model) {
		model.put("settings", settings);
		
		if(result.hasErrors()) {
			model.put("error", "Enter at least 10");
			return "todo";
		}
		
		User user = userService.GetAuthenticatedUser();
    	todo.setUser(user);
    	todo.setCreation_date(new Date());
		todoRepository.save(todo);
		
		redirectAttributes.addFlashAttribute("flash", 
				new FlashMessage("Todo created successfully", FlashMessage.Status.success));
		return "redirect:/@" + user.getUsername();
	}
	
	
	@GetMapping("/update-todo")
	public String ShowUpdateTodoPage(ModelMap model, @RequestParam Long id) {
		model.put("PageTitle", "Update Todo");
		model.put("settings", settings);
		model.put("todo", todoRepository.getOne(id));
		return "todo";
	}
	
	@PostMapping("/update-todo")
	public String UpdateTodo(@Valid Todo todo, BindingResult result,
			RedirectAttributes redirectAttributes, ModelMap model) {
		model.put("settings", settings);
		
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
	
	
	@GetMapping("/todo-state")
	public String changeState(ModelMap model, @RequestParam Long id) {
		User user = userService.GetAuthenticatedUser();
		Todo todo = todoRepository.getOne(id);
		todo.setCompleted(!todo.isCompleted());
		todoRepository.save(todo);
		return "redirect:/@" + user.getUsername();
	}
	
	
	@GetMapping("/search")
	public String SearchTodo(ModelMap model, 
			@RequestParam(defaultValue="") String q) {
		model.put("settings", settings);
		model.put("PageTitle", "Search for: " + q);
		
		List<Todo> todos = new ArrayList<Todo>();
		for (Todo todo : todoRepository.findAllByLoggedinUser()) {
			if(todo.getDesc().toLowerCase().contains(q))
				todos.add(todo);
		}
		
		model.put("todos", todos);
		model.put("result", todos.size() + " results found for <mark>" + q + "</mark>");
		return "search";
	}
}
