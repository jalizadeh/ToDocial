package com.jalizadeh.todocial.api;

import com.jalizadeh.todocial.model.todo.Todo;
import com.jalizadeh.todocial.model.todo.TodoLog;
import com.jalizadeh.todocial.model.todo.dto.InputLog;
import com.jalizadeh.todocial.model.todo.dto.InputTodo;
import com.jalizadeh.todocial.model.todo.dto.TodoDto;
import com.jalizadeh.todocial.service.TodoService;
import com.jalizadeh.todocial.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.jalizadeh.todocial.utils.DataUtils.mapTodoToDTO;
import static com.jalizadeh.todocial.utils.DataUtils.mapTodoToLogDTO;

@RestController
@RequestMapping("/api/v1/todo")
public class TodoApi {

	@Autowired
	private TodoService todoService;

	@GetMapping("/id/{id}")
	public ResponseEntity<?> getTodo(@PathVariable("id") Long id) {
		Todo todo = todoService.findById(id);

		if(todo != null){
			return new ResponseEntity<>(mapTodoToDTO(todo),HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping
	public List<TodoDto> getAllTodos() {
		return todoService.findAll().stream()
				.map(DataUtils::mapTodoToDTO).collect(Collectors.toList());
	}

	@GetMapping("/{username}")
	public List<TodoDto> getUserTodo(@PathVariable("username") String username) {
		return todoService.findTodosByUsername(username).stream()
				.map(DataUtils::mapTodoToDTO)
				.collect(Collectors.toList());
	}

	@GetMapping("/me")
	public List<TodoDto> getCurrentUserTodo() {
		return todoService.findAllByLoggedinUser().stream()
				.map(DataUtils::mapTodoToDTO).collect(Collectors.toList());
	}

	@PostMapping
	public ResponseEntity<TodoDto> createTodo(@RequestBody InputTodo todo) {
		Todo createdTodo = todoService.createTodo(todo);
		return new ResponseEntity<>(mapTodoToDTO(createdTodo), HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> cancelTodo(@PathVariable("id") Long id) {
		boolean canceled = todoService.cancelTodo(id);

		if(canceled){
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}/db")
	public ResponseEntity<?> deleteTodoFromDB(@PathVariable("id") Long id) {
		boolean deleted = todoService.deleteTodoById(id);

		if(deleted){
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/{todoId}/log/{todoLogId}")
	public ResponseEntity<?> getTodoLog(@PathVariable("todoId") Long todoId, @PathVariable("todoLogId") Long todoLogId) {
		TodoLog todoLog = todoService.findTodoLogById(todoId, todoLogId);

		if(todoLog != null){
			return new ResponseEntity<>(mapTodoToLogDTO(todoLog),HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/{todoId}/log")
	public  ResponseEntity<?> createTodoLog(@PathVariable("todoId") Long todoId, @RequestBody InputLog log) {
		TodoLog created = todoService.createTodoLog(todoId, log);

		if(created != null){
			return new ResponseEntity<>(mapTodoToLogDTO(created),HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@DeleteMapping("/{todoId}/log/{todoLogId}")
	public  ResponseEntity<?> deleteTodoLog(@PathVariable("todoId") Long todoId, @PathVariable("todoLogId") Long todoLogId) {
		return todoService.deleteTodoLog(todoId, todoLogId) ?
			new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
