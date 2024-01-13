package com.jalizadeh.todocial.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.jalizadeh.todocial.web.repository.GymPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jalizadeh.todocial.web.controller.admin.model.SettingsGeneralConfig;
import com.jalizadeh.todocial.web.model.FlashMessage;
import com.jalizadeh.todocial.web.model.Todo;
import com.jalizadeh.todocial.web.model.TodoLog;
import com.jalizadeh.todocial.web.model.User;
import com.jalizadeh.todocial.system.repository.TodoLogRepository;
import com.jalizadeh.todocial.system.repository.TodoRepository;
import com.jalizadeh.todocial.system.repository.UserRepository;
import com.jalizadeh.todocial.system.service.CommonServices;
import com.jalizadeh.todocial.system.service.UserService;

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

	@Autowired
	private GymPlanRepository gymPlanRepository;
	
	
	@InitBinder
	protected void InitBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	

	
	/**
	 * If user is authenticated, he will be redirected to his personal page,
	 * but if it is an anonymous user, can access the public area of
	 * a registered user's page
	 */
	@GetMapping("/@{username}")
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

		//if the user is anonymous
		if (utilites.isUserAnonymous()) {
			model.put("user", targetUser); 
			model.put("todos", todoRepository.findAllByUserIdAndPubliccTrue(targetUser.getId()));
			String pageTitle = targetUser.getFirstname() + " " + targetUser.getLastname() + "(" + targetUser.getUsername() + ")";
			model.put("PageTitle", pageTitle);
			return "public-page";
		}
		
		
		User loggedinUser  =  userRepository.findByUsername(currentUser.getUsername());

		//the user is logged in and is checking another profile
		//but first check if current user is following the target or not
		if(!currentUser.getUsername().equals(username)) {
			List<String> listOfFollowings = loggedinUser.getFollowings().stream()
						.map(u -> u.getUsername())
						.collect(Collectors.toList());
			
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
			model.put("gym", gymPlanRepository.findAll());
			model.put("PageTitle", targetUser.getFirstname() + " " + targetUser.getLastname() + "(" + targetUser.getUsername() + ")");
			return "public-page";
		}
		

		model.put("LoggedinUsers", userService.getAllLoggedinUsers());
		model.put("user", targetUser);
		model.put("todosCompleted", todoRepository.getAllCompleted());
		model.put("todosNotCompleted", todoRepository.getAllNotCompleted());
		model.put("todosCanceled", todoRepository.getAllCanceled());
		model.put("PageTitle", "My Todos");
		return "my-todos";
	}
	
	
	@PostMapping("/@{username}")
	public String AddNewTodoLog(ModelMap model, @RequestParam Long todoId, 
			@RequestParam String log) {

		User user = userService.GetAuthenticatedUser();

		//TODO: check for security+null
		
		TodoLog todoLog = new TodoLog(new Date(), log);
		Todo todo = todoRepository.getOne(todoId);
		
		todoLog.getTodos().add(todo);
		todo.getLogs().add(todoLog);
		
		todoRepository.save(todo);
		
		return "redirect:/@" + user.getUsername();
	}
	
	
	@GetMapping("/cancel-todo")
	public String DeleteTodo(ModelMap model, @RequestParam Long id) {
		Todo todo = todoRepository.getOne(id);
		todo.setCanceled(true);
		todo.setCancel_date(new Date());
		todoRepository.save(todo);
		return "redirect:/@" + todo.getUser().getUsername();
	}
	
	
	@GetMapping("/resume-todo")
	public String ResumeTodo(ModelMap model, @RequestParam Long id) {
		Todo todo = todoRepository.getOne(id);
		todo.setCanceled(false);
		//todo.setCancel_date(new Date());
		todoRepository.save(todo);
		return "redirect:/@" + todo.getUser().getUsername();
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
		model.addAttribute("allPriority",allPriority());
		model.addAttribute("allType",allType());
		return "todo";
	}

	
	@PostMapping("/add-todo")
	public String AddTodo(@Valid Todo todo, BindingResult result,
			RedirectAttributes redirectAttributes, ModelMap model) {
		model.put("settings", settings);
		
		if(result.hasErrors()) {
			model.put("error", result.getAllErrors());
			model.addAttribute("allPriority",allPriority());
			model.addAttribute("allType",allType());
			return "todo";
		}
		
		User user = userService.GetAuthenticatedUser();
    	todo.setUser(user);
    	//todo.setCreation_date(new Date());
		todoRepository.save(todo);
		
		redirectAttributes.addFlashAttribute("flash", 
				new FlashMessage("Todo created successfully", FlashMessage.Status.success));
		return "redirect:/@" + user.getUsername();
	}


	/**
	 * 1. exists?
	 * 2. show mine as editable
	 * 3. show sb else as viewable
	 */
	@GetMapping("/todo")
	public String ShowUpdateTodoPage(ModelMap model, @RequestParam Long id, RedirectAttributes redirectAttributes) {
		Optional<Todo> todo = todoRepository.findById(id);

		//if requested to-do id doesnt exists
		if(!todo.isPresent()){
			redirectAttributes.addFlashAttribute("exception", "The requested todo with id "+ id + " doesn't exist");
			return "redirect:/error";
		}

		Todo foundTodo = todo.get();
		model.put("PageTitle", foundTodo.getName());
		model.put("settings", settings);
		model.put("todo", foundTodo);
		model.addAttribute("allPriority",allPriority());
		model.addAttribute("allType",allType());

		//to-do doesnt belongs to current logged in user. It is viewable only, not editable
		User currentUser = userService.GetAuthenticatedUser();
		if(!foundTodo.getUser().getUsername().equals(currentUser.getUsername())){
			return "todo-completed";
		}

		//to-do belongs to current logged in user. By default shows as editable
		if(foundTodo.isCompleted())
			return "todo-completed";

		return "todo";
	}
	
	@PostMapping("/todo")
	public String UpdateTodo(@Valid Todo todo, BindingResult result, RedirectAttributes redirectAttributes, ModelMap model) {
		model.put("settings", settings);
		
		if(result.hasErrors()) {
			model.put("error", result.getAllErrors());
			model.addAttribute("allPriority",allPriority());
			model.addAttribute("allType",allType());
			return "todo";
		}

		User user = userService.GetAuthenticatedUser();
		Todo ref = todoRepository.getOne(todo.getId());
		
		todo.setUser(user);
		todo.setLike(ref.getLike());
		//todo.setCreation_date(ref.getCreation_date());
		todo.setLogs(ref.getLogs());
		todoRepository.save(todo);
		
		redirectAttributes.addFlashAttribute("flash", 
				new FlashMessage("Todo updated successfully", FlashMessage.Status.success));
		return "redirect:/@" + user.getUsername();
	}
	
	
	@PostMapping("/update-todo-newlog")
	public String UpdateTodoNewLog(ModelMap model, @RequestParam Long todoId, 
			@RequestParam String log) {

		//TODO: check for security+null
		
		TodoLog todoLog = new TodoLog(new Date(), log);
		Todo todo = todoRepository.getOne(todoId);
		
		todoLog.getTodos().add(todo);
		todo.getLogs().add(todoLog);
		
		todoRepository.save(todo);
		
		return "redirect:/update-todo?id=" + todoId;
	}
	
	
	@GetMapping("/update-todo-deletelog")
	public String UpdateTodoDeleteLog(ModelMap model, @RequestParam Long todoId, 
			@RequestParam Long logId) {
		todoLogRepository.deleteById(logId);
		return "redirect:/update-todo?id=" + todoId;
	}
	
	
	@GetMapping("/complete-todo")
	public String ShowCompleteTodo(ModelMap model, @RequestParam Long id) {
		model.put("PageTitle", "Complete & Archive Todo");
		model.put("settings", settings);
		
		Todo todo = todoRepository.getOne(id);
		todo.setCompletion_date(new Date());
		todo.setCompleted(true);
		model.put("todo", todo);
		
		model.addAttribute("allPriority",allPriority());
		model.addAttribute("allType",allType());
		return "todo";
	}
	
	
	@PostMapping("/complete-todo")
	public String SetCompleteTodo(@Valid Todo todo, BindingResult result,
			RedirectAttributes redirectAttributes, ModelMap model) {

		if(result.hasErrors()) {
			model.put("error", result.getAllErrors());
			model.put("settings", settings);
			model.put("PageTitle", "Complete & Archive Todo");
			model.addAttribute("allPriority",allPriority());
			model.addAttribute("allType",allType());
			return "todo";
		}
		
		Todo ref = todoRepository.getOne(todo.getId());
		todo.setCreation_date(ref.getCreation_date());
		todo.setCompletion_date(new Date());
		todo.setCompleted(true);
		todo.setUser(ref.getUser());
		todo.setLogs(ref.getLogs());
		
		todoRepository.save(todo);
		
		redirectAttributes.addFlashAttribute("flash", 
				new FlashMessage("Todo completed successfully", FlashMessage.Status.success));
		return "redirect:/@" + ref.getUser().getUsername();
	}
	
	
	@GetMapping("/completed-todo")
	public String ShowCompletedTodo(ModelMap model, @RequestParam Long id,
			RedirectAttributes redirectAttributes) {
		model.put("PageTitle", "Completed Todo");
		model.put("settings", settings);
		
		Todo todo = todoRepository.getOne(id);
		if(!todo.isCompleted()) {
			redirectAttributes.addFlashAttribute("flash", 
					new FlashMessage("Todo is not completed yet.", FlashMessage.Status.warning));
			return "redirect:/@" + todo.getUser().getUsername();
		}
		
		model.put("todo", todo);
		return "todo-completed";
	}
	
	
	
	
	@GetMapping("/todo-state")
	public String changeState(ModelMap model, @RequestParam Long id) {
		User user = userService.GetAuthenticatedUser();
		Todo todo = todoRepository.getOne(id);
		todo.setCompleted(!todo.isCompleted());
		todoRepository.save(todo);
		return "redirect:/@" + user.getUsername();
	}
	

	//===============Methods==========================
	private List<String>  allPriority() {
		List<String> allPriority = new ArrayList<>();
		allPriority.add("Emergency");
		allPriority.add("High");
		allPriority.add("Medium");
		allPriority.add("Low");
		return allPriority;
	}



	private List<String> allType() {
		List<String> allType = new ArrayList<>();
		allType.add("Book");
		allType.add("Fun");
		allType.add("Improvement");
		allType.add("Job");
		allType.add("Learning");
		allType.add("Life");
		return allType;
	}
}
