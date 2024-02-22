package com.jalizadeh.todocial.service.impl;

import com.jalizadeh.todocial.model.eventline.Event;
import com.jalizadeh.todocial.model.todo.Todo;
import com.jalizadeh.todocial.model.todo.TodoLog;
import com.jalizadeh.todocial.model.todo.dto.InputLog;
import com.jalizadeh.todocial.model.todo.dto.InputTodo;
import com.jalizadeh.todocial.model.user.User;
import com.jalizadeh.todocial.repository.todo.TodoLogRepository;
import com.jalizadeh.todocial.repository.todo.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TodoService {

    public static final int CURRENT_YEAR = 2024;

    @Autowired
    private UserService userService;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TodoLogRepository todoLogRepository;


    public Todo findById(Long id) {
        User loggedInUser = userService.getAuthenticatedUser();
        return todoRepository.findById(id).orElse(null);

        /*
        if (foundTodo == null || !foundTodo.getUser().getId().equals(loggedInUser.getId()))
            return null;

        return foundTodo;
         */
    }

    public TodoLog saveNewLog(String log) {
        return todoLogRepository.save(new TodoLog(new Date(), log));
    }

    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    public List<Todo> findAllByUser(User user) {
        return todoRepository.findAllByUser(user);
    }

    public List<Todo> findTodosByUsername(String username) {
        User user = userService.findByUsername(username);
        return todoRepository.findAllByUser(user);
    }

    public List<Todo> findAllByLoggedinUser() {
        User user = userService.getAuthenticatedUser();
        return todoRepository.findAllByUser(user);
    }

    public Todo createTodo(InputTodo todo) {
        User user = userService.getAuthenticatedUser();

        Todo newTodo = new Todo();
        newTodo.setName(todo.getName());
        newTodo.setDescription(todo.getDescription());
        newTodo.setReason(todo.getReason());
        newTodo.setUser(user);
        newTodo.setCreation_date(new Date());

        return todoRepository.save(newTodo);
    }

    public boolean cancelTodo(Long id) {
        User loggedinUser = userService.getAuthenticatedUser();
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
        User loggedInUser = userService.getAuthenticatedUser();
        Todo foundTodo = todoRepository.findById(id).orElse(null);

        if (foundTodo == null || !foundTodo.getUser().getId().equals(loggedInUser.getId()))
            return false;

        todoRepository.delete(foundTodo);
        return true;
    }

    public TodoLog createTodoLog(Long id, InputLog log) {
        User loggedInUser = userService.getAuthenticatedUser();
        Todo foundTodo = todoRepository.findById(id).orElse(null);
        if (foundTodo == null || !foundTodo.getUser().getId().equals(loggedInUser.getId()))
            return null;

        TodoLog todoLog = new TodoLog(new Date(), log.getLog());
        todoLog.getTodos().add(foundTodo);
        foundTodo.getLogs().add(todoLog);
        TodoLog createdTodoLog = todoLogRepository.save(todoLog);
        todoRepository.save(foundTodo);

        return createdTodoLog;
    }

    public boolean deleteTodoLog(Long todoId, Long todoLogId) {
        User loggedInUser = userService.getAuthenticatedUser();
        Todo foundTodo = todoRepository.findById(todoId).orElse(null);
        if (foundTodo == null || !foundTodo.getUser().getId().equals(loggedInUser.getId()))
            return false;

        TodoLog foundTodoLog = todoLogRepository.findById(todoLogId).orElse(null);
        if (foundTodoLog == null)
            return false;

        todoLogRepository.delete(foundTodoLog);
        return true;
    }

    public TodoLog findTodoLogById(Long todoId, Long todoLogId) {
        User loggedInUser = userService.getAuthenticatedUser();

        Todo foundTodo = todoRepository.findById(todoId).orElse(null);
        if (foundTodo == null || !foundTodo.getUser().getId().equals(loggedInUser.getId()))
            return null;

        return todoLogRepository.findById(todoLogId).orElse(null);
    }

    public List<Todo> findAllTodosByIsPublicTrue() {
        return todoRepository.findAllByIsPublicTrue();
    }

    public List<Todo> findAllTodosByUserIdAndIsPublicTrue(Long id) {
        return todoRepository.findAllByUserIdAndIsPublicTrue(id);
    }

    public List<Todo> searchAllTodosByLoggedinUser(String q) {
        return todoRepository.searchAllByLoggedinUser(q);
    }

    public List<Todo> getAllCompletedTodos() {
        return todoRepository.getAllCompleted();
    }

    public List<Todo> getAllNotCompletedTodos() {
        return todoRepository.getAllNotCompleted();
    }

    public List<Todo> getAllCanceledTodos() {
        return todoRepository.getAllCanceled();
    }


    public void save(Todo todo) {
        todoRepository.save(todo);
    }

    public void deleteById(Long id) {
        todoRepository.deleteById(id);
    }

    public void deleteLogById(Long id) {
        todoLogRepository.deleteById(id);
    }

    public List<Todo> findAllPublicByMonth(int monthIndex) {
        // Create LocalDateTime object for the first day of the specified month
        LocalDateTime startDateTime = LocalDateTime.of(getCurrentYear(monthIndex), Month.of(monthIndex), 1, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(getCurrentYear(monthIndex), Month.of(monthIndex).plus(1), 1, 0, 0);

        // Convert LocalDateTime objects to Unix timestamps in seconds
        long startTimestamp = startDateTime.toEpochSecond(ZoneOffset.UTC);
        long endTimestamp = endDateTime.toEpochSecond(ZoneOffset.UTC);

        // Convert Unix timestamps to Date objects
        Date startDate = new Date(startTimestamp * 1000); // Multiply by 1000 to convert seconds to milliseconds
        Date endDate = new Date(endTimestamp * 1000); // Multiply by 1000 to convert seconds to milliseconds

        return todoRepository.findAllPublicBetween(startDate, endDate);
    }

    //current year value is used for find results according to [startDate, endDate), so if the month is 12, so it should be next year
    private int getCurrentYear(int monthIndex) {
        return monthIndex != 12 ? CURRENT_YEAR : CURRENT_YEAR + 1;
    }

    public List<Event> findAllByMonthDay(int month, int day) {
        return null;
    }

    public String getMonthName(int monthIndex) {
        return Month.of(monthIndex).toString();
    }


    public int getMonthDays(int monthIndex) {
        return YearMonth.of(getCurrentYear(monthIndex), monthIndex).lengthOfMonth();
    }

    public int getDayOfMonth(Date date) {
        // Create a Calendar instance and set the time to the given date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // Get the day of the month
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        return dayOfMonth;
    }
}
