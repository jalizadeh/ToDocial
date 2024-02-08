package com.jalizadeh.todocial.service;

import com.jalizadeh.todocial.model.todo.Todo;
import com.jalizadeh.todocial.model.todo.TodoLog;
import com.jalizadeh.todocial.model.todo.dto.InputLog;
import com.jalizadeh.todocial.model.todo.dto.InputTodo;
import com.jalizadeh.todocial.model.user.User;
import com.jalizadeh.todocial.repository.todo.TodoLogRepository;
import com.jalizadeh.todocial.repository.todo.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TodoService {

    @Autowired
    private UserService userService;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TodoLogRepository todoLogRepository;


    public Todo findById(Long id) {
        User loggedInUser = userService.GetAuthenticatedUser();
        Todo foundTodo = todoRepository.findById(id).orElse(null);

        if (foundTodo == null)
            return null;

        if (!foundTodo.getUser().getId().equals(loggedInUser.getId()))
            return null; //forbidden

        return foundTodo;
    }

    public TodoLog saveNewLog(String log) {
        return todoLogRepository.save(new TodoLog(new Date(), log));
    }

    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    public List<Todo> findTodosByUsername(String username) {
        User user = userService.findByUsername(username);
        return todoRepository.findAllByUser(user);
    }

    public List<Todo> findAllByLoggedinUser() {
        User user = userService.GetAuthenticatedUser();
        return todoRepository.findAllByUser(user);
    }

    public Todo createTodo(InputTodo todo) {
        User user = userService.GetAuthenticatedUser();

        Todo newTodo = new Todo();
        newTodo.setName(todo.getName());
        newTodo.setDescription(todo.getDescription());
        newTodo.setReason(todo.getReason());
        newTodo.setUser(user);
        newTodo.setCreation_date(new Date());

        return todoRepository.save(newTodo);
    }

    public boolean cancelTodo(Long id) {
        User loggedinUser = userService.GetAuthenticatedUser();
        Todo todo = todoRepository.findById(id).orElse(null);

        if (todo != null && todo.getUser().getId().equals(loggedinUser.getId())) {
            todo.setCanceled(true);
            todo.setCancel_date(new Date());
            todoRepository.save(todo);
            return true;
        }

        return false;
    }

    public boolean deleteTodoById(Long id) {
        User loggedInUser = userService.GetAuthenticatedUser();
        Todo foundTodo = todoRepository.findById(id).orElse(null);

        if (foundTodo == null)
            return false;

        if (!foundTodo.getUser().getId().equals(loggedInUser.getId()))
            return false; //forbidden

        todoRepository.delete(foundTodo);
        return true;
    }

    public TodoLog createTodoLog(Long id, InputLog log) {
        User loggedInUser = userService.GetAuthenticatedUser();
        Todo foundTodo = todoRepository.findById(id).orElse(null);

        if (foundTodo == null)
            return null;

        if (!foundTodo.getUser().getId().equals(loggedInUser.getId()))
            return null; //forbidden

        TodoLog todoLog = new TodoLog(new Date(), log.getLog());
        todoLog.getTodos().add(foundTodo);
        foundTodo.getLogs().add(todoLog);
        TodoLog createdTodoLog = todoLogRepository.save(todoLog);
        todoRepository.save(foundTodo);

        return createdTodoLog;
    }

    public boolean deleteTodoLog(Long todoId, Long todoLogId) {
        User loggedInUser = userService.GetAuthenticatedUser();
        Todo foundTodo = todoRepository.findById(todoId).orElse(null);
        TodoLog foundTodoLog = todoLogRepository.findById(todoLogId).orElse(null);

        if (foundTodo == null || foundTodoLog == null)
            return false;

        if (!foundTodo.getUser().getId().equals(loggedInUser.getId()))
            return false;

        todoLogRepository.delete(foundTodoLog);
        return true;
    }
}
