package com.jalizadeh.todocial.api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TestModel_User_Request{
	private String firstname;
	private String lastname;
	private String username;
	private String email;
	private String password;
}
