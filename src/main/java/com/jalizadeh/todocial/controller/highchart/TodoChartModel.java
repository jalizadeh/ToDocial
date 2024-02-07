package com.jalizadeh.todocial.controller.highchart;

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
