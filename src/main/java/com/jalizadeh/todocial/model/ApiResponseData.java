package com.jalizadeh.todocial.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseData<T> {

    private HttpStatus status;
    private String message;
    private T data;
}
