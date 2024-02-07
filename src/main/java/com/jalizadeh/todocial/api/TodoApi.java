package com.jalizadeh.todocial.api;

import com.jalizadeh.todocial.model.todo.Todo;
import com.jalizadeh.todocial.model.todo.TodoLog;
import com.jalizadeh.todocial.model.todo.dto.InputLog;
import com.jalizadeh.todocial.model.todo.dto.InputTodo;
import com.jalizadeh.todocial.model.todo.dto.TodoDto;
import com.jalizadeh.todocial.model.todo.dto.TodoLogDto;
import com.jalizadeh.todocial.model.user.User;
import com.jalizadeh.todocial.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/todo")
public class TodoApi {

	@Autowired
	private TodoService todoService;
	
	@GetMapping
	public List<TodoDto> getAllTodos() {
		return todoService.findAll().stream()
				.map(this::mapTodoToDTO).collect(Collectors.toList());
	}

	@GetMapping("/{username}")
	public List<TodoDto> getUserTodo(@PathVariable("username") String username) {
		return todoService.findTodosByUsername(username).stream()
				.map(this::mapTodoToDTO)
				.collect(Collectors.toList());
	}

	@GetMapping("/me")
	public List<TodoDto> getUserTodo() {
		return todoService.findAllByLoggedinUser().stream()
				.map(this::mapTodoToDTO).collect(Collectors.toList());
	}

	@PostMapping
	public TodoDto createTodo(@RequestBody InputTodo todo) {
		Todo createdTodo = todoService.createTodo(todo);
		return mapTodoToDTO(createdTodo);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> cancelTodo(@PathVariable("id") Long id) {
		boolean canceled = todoService.cancelTodo(id);

		if(canceled){
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}/db")
	public ResponseEntity<String> deleteTodoFromDB(@PathVariable("id") Long id) {
		boolean deleted = todoService.deleteTodoById(id);

		if(deleted){
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	

	@PostMapping("/{id}/log")
	public  ResponseEntity<?> createTodoLog(@PathVariable("id") Long id, @RequestBody InputLog log) {
		TodoLog created = todoService.createTodoLog(id, log);

		if(created != null){
			return new ResponseEntity<TodoLogDto>(mapTodoToLogDTO(created),HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@DeleteMapping("/{todoId}/log/{todoLogId}")
	public  ResponseEntity<String> deleteTodoLog(@PathVariable("todoId") Long todoId, @PathVariable("todoLogId") Long todoLogId) {
		boolean deleted = todoService.deleteTodoLog(todoId, todoLogId);

		if(deleted){
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	

	private TodoDto mapTodoToDTO(Todo t) {
		return new TodoDto(t.getId(), t.getName(), t.getDescription(), t.getReason(), new ArrayList<>(), t.getLike(),
				t.isCompleted(), t.isCanceled(), t.getIsPublic());
	}

	private TodoLogDto mapTodoToLogDTO(TodoLog l) {
		return new TodoLogDto(l.getId(), l.getLog(), l.getLogDate());
	}
	
}
