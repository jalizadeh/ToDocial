package com.jalizadeh.todocial.api.controllers.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TestModel_TodoLog_Response{
	private Long id;
	private String log; 
	private Date logDate;
}