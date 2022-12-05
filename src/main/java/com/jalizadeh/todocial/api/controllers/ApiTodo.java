package com.jalizadeh.todocial.api.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jalizadeh.todocial.web.model.Todo;
import com.jalizadeh.todocial.web.repository.TodoRepository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
public class ApiTodo {

	@Autowired
	private TodoRepository todoRepository;
	
	@GetMapping("/api/v1/todo")
	public List<TodoDTO> getAllTodo() {
		return todoRepository.findAll().stream()
				.map(t -> mapTodoToDTO(t)).collect(Collectors.toList());
	}
	
	
	private TodoDTO mapTodoToDTO(Todo t) {
		return new TodoDTO(
					t.getId(), t.getName(), t.getDescription(), t.getReason(),t.isCompleted()
				);
	}


	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	static class TodoDTO{
		private Long id;
		private String name; 
		private String description;
		private String reason;
		private boolean completed;
	}
	
}
