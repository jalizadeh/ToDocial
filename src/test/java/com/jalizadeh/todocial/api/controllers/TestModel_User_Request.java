package com.jalizadeh.todocial.api.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
class TestModel_User_Request{
	private String firstname;
	private String lastname;
	private String username;
	private String email;
	private String password;
}
