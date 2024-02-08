package com.jalizadeh.todocial.model.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TodoLogDto {
    private Long id;
    private String log;
    private Date logDate;
}
