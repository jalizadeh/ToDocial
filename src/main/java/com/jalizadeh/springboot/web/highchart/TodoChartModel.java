package com.jalizadeh.springboot.web.highchart;

import java.util.Date;

public class TodoChartModel {

	private String name;
	private Date start;
	private Date end;
	private String progress;
	

	public TodoChartModel(String name, Date start, Date end, String progress) {
		super();
		this.name = name;
		this.start = start;
		this.end = end;
		this.progress = progress;
	}

	public String getName() {
		return name;
	}

	public Date getStart() {
		return start;
	}

	public Date getEnd() {
		return end;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}
	
	
}
