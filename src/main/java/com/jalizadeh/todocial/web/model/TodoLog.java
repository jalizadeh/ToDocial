package com.jalizadeh.todocial.web.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class TodoLog {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private Date logDate;
	
	private String log;
	
	@ManyToMany(mappedBy="logs")
	private Collection<Todo> todos;

	public TodoLog() {
		super();
	}

	public TodoLog(Date logDate, String log) {
		super();
		this.logDate = logDate;
		this.log = log;
		this.todos = new ArrayList<Todo>();
	}

	public Long getId() {
		return id;
	}

	public Date getLogDate() {
		return logDate;
	}

	public String getLog() {
		return log;
	}

	public Collection<Todo> getTodos() {
		return todos;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public void setTodos(Collection<Todo> todos) {
		this.todos = todos;
	}
}
