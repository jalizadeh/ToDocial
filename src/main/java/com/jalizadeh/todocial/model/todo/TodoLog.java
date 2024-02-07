package com.jalizadeh.todocial.model.todo;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
@Data
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
		this.logDate = logDate;
		this.log = log;
		this.todos = new ArrayList<Todo>();
	}

}
