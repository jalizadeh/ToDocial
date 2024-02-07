package com.jalizadeh.todocial.model.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InputUser{
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String password;
}