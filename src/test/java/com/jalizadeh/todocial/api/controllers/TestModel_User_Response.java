package com.jalizadeh.todocial.api.controllers;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class TestModel_User_Response{
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
