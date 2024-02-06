package com.jalizadeh.todocial.api.controllers.models;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TestModel_Todo_Response{
	private Long id;
	private String name; 
	private String description;
	private String reason;
	private List<TestModel_TodoLog_Response> logs;
	private Long like;
	private boolean completed;
	private boolean canceled;
	private boolean publicc;
}
