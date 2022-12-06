package com.jalizadeh.todocial.api.controllers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jalizadeh.todocial.system.repository.TodoRepository;
import com.jalizadeh.todocial.system.repository.UserRepository;
import com.jalizadeh.todocial.system.service.UserService;
import com.jalizadeh.todocial.web.model.Todo;
import com.jalizadeh.todocial.web.model.TodoLog;
import com.jalizadeh.todocial.web.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
public class ApiTodo {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TodoRepository todoRepository;
	
	@GetMapping("/api/v1/todo")
	public List<TodoDTO> getAllTodo() {
		return todoRepository.findAll().stream()
				.map(t -> mapTodoToDTO(t)).collect(Collectors.toList());
	}
	
	@GetMapping("/api/v1/todo/{username}")
	public List<TodoDTO> getUserTodo(@PathVariable("username") String username){
		User user = userRepository.findByUsername(username);
		return todoRepository.findAllByUserId(user.getId()).stream()
					.map(t -> mapTodoToDTO(t)).collect(Collectors.toList());
	}
	
	@GetMapping("/api/v1/todo/me")
	public List<TodoDTO> getUserTodo(){
		return todoRepository.findAllByLoggedinUser().stream()
					.map(t -> mapTodoToDTO(t)).collect(Collectors.toList());
	}
	
	
	@PostMapping("/api/v1/todo")
	public TodoDTO createTodo(@RequestBody InputTodo todo){
		//User currentUser = userRepository.findLoggedinUser();
		User user = userService.GetAuthenticatedUser();
		
		Todo newTodo = new Todo();
		newTodo.setName(todo.name);
		newTodo.setDescription(todo.description);
		newTodo.setReason(todo.reason);
		newTodo.setUser(user);
		newTodo.setCreation_date(new Date());
		Todo savedTodo = todoRepository.save(newTodo);
		
		return mapTodoToDTO(savedTodo);
	}
	
	
	@DeleteMapping("/api/v1/todo/{id}")
	public ResponseEntity<String> cancelTodo(@PathVariable("id") Long id){
		User loggedinUser = userService.GetAuthenticatedUser();
		Todo todo = todoRepository.getOne(id);
		
		if(todo.getUser().getId() == loggedinUser.getId()) {
			todo.setCanceled(true);
			todo.setCancel_date(new Date());
			todoRepository.save(todo);
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		
		return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
	}
	
	
	
	@PostMapping("/api/v1/todo/{id}/log")
	public ResponseEntity<String> createTodoLog(@PathVariable("id") Long id, @RequestBody InputLog log){
		User loggedinUser = userService.GetAuthenticatedUser();
		Todo todo = todoRepository.getOne(id);
		
		if(todo.getUser().getId() == loggedinUser.getId()) {
			TodoLog todoLog = new TodoLog(new Date(), log.log);
			todoLog.getTodos().add(todo);
			todo.getLogs().add(todoLog);
			todoRepository.save(todo);
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		
		return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
	}
	
	
	
	private TodoDTO mapTodoToDTO(Todo t) {
		return new TodoDTO(
					t.getId(), t.getName(), t.getDescription(), t.getReason(), 
					t.getLogs().stream().map(i -> mapTodoToLogDTO(i)).collect(Collectors.toList()),
					t.getLike(),t.isCompleted(), t.isCanceled(), t.isPublicc()
				);
	}
	
	private TodoLogDTO mapTodoToLogDTO(TodoLog l) {
		return new TodoLogDTO(l.getId(), l.getLog(), l.getLogDate());
	}


	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	static class TodoDTO{
		private Long id;
		private String name; 
		private String description;
		private String reason;
		private List<TodoLogDTO> logs;
		private Long like;
		private boolean completed;
		private boolean canceled;
		private boolean publicc;
	}
	
	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	static class InputTodo{
		private String name; 
		private String description;
		private String reason;
	}
	
	
	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	static class TodoLogDTO{
		private Long id;
		private String log; 
		private Date logDate;
	}
	
	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	static class InputLog{
		private String log; 
	}
	
	
}
