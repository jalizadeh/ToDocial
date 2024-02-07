package com.jalizadeh.todocial.model.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InputTodo {
    private String name;
    private String description;
    private String reason;
}
