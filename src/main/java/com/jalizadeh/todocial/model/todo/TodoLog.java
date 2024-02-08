package com.jalizadeh.todocial.model.todo;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.*;

@Entity
@Data
public class TodoLog {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private Date logDate;
	
	private String log;
	
	@ManyToMany(mappedBy="logs", cascade = CascadeType.REMOVE)
	private Collection<Todo> todos;

	public TodoLog() {
		super();
	}

	public TodoLog(Date logDate, String log) {
		this.logDate = logDate;
		this.log = log;
		this.todos = new ArrayList<>();
	}

}
