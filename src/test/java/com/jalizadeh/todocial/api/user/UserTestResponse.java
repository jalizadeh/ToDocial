package com.jalizadeh.todocial.api.user;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
class UserTestResponse{
	private Long id;
	private String firstname;
	private String lastname;
	private String username;
	private String email;
	private boolean enabled;
	private String photo;
	private List<String> followers;
	private List<String> followings;
}
