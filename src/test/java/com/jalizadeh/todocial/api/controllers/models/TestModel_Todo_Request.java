package com.jalizadeh.todocial.api.controllers.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TestModel_Todo_Request {
	private String name; 
	private String description;
	private String reason;
}
