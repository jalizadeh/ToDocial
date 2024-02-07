package com.jalizadeh.todocial.model.todo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TodoChartModel {

	private String name;
	private Date start;
	private Date end;
	private String progress;

}
