package com.jalizadeh.todocial.utils;

import com.jalizadeh.todocial.model.todo.Todo;
import com.jalizadeh.todocial.model.todo.TodoLog;
import com.jalizadeh.todocial.model.todo.dto.TodoDto;
import com.jalizadeh.todocial.model.todo.dto.TodoLogDto;
import com.jalizadeh.todocial.model.user.dto.UserDto;
import com.jalizadeh.todocial.model.user.User;
import org.owasp.encoder.Encode;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class DataUtils {

    public static String sanitizeQuery(String query) {
        // Use OWASP Java Encoder to HTML encode the user input
        return Encode.forHtml(query);
    }

    public static UserDto mapUserToDTO(User u) {
        if(u == null) return null;

        return new UserDto(
                u.getId(), u.getFirstname(), u.getLastname(),
                u.getUsername() ,u.getEmail(), u.isEnabled(), u.getPhoto(),
                u.getFollowers() != null ? u.getFollowers().stream().map(User::getUsername).collect(Collectors.toList()) : new ArrayList<>(),
                u.getFollowings() != null ? u.getFollowings().stream().map(User::getUsername).collect(Collectors.toList()) : new ArrayList<>()
        );
    }

    public static TodoDto mapTodoToDTO(Todo t) {
        return new TodoDto(t.getId(), t.getName(), t.getDescription(), t.getReason(),
                new ArrayList<>(), t.getLike(),
                t.isCompleted(), t.isCanceled(), t.getIsPublic());
    }

    public static TodoLogDto mapTodoToLogDTO(TodoLog l) {
        return new TodoLogDto(l.getId(), l.getLog(), l.getLogDate());
    }
}
