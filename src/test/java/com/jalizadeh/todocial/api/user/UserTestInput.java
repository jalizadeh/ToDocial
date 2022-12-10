package com.jalizadeh.todocial.api.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
class UserTestInput{
	private String firstname;
	private String lastname;
	private String username;
	private String email;
	private String password;
}
