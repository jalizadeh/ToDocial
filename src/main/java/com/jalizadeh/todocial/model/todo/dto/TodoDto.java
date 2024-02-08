package com.jalizadeh.todocial.model.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TodoDto {
    private Long id;
    private String name;
    private String description;
    private String reason;
    private List<TodoLogDto> logs;
    private Long like;
    private boolean completed;
    private boolean canceled;
    private boolean publicc;
}
