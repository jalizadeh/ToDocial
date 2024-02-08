package com.jalizadeh.todocial.model.todo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class TodoLog {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private Date logDate;
	
	private String log;
	
	@ManyToMany(mappedBy="logs")
	@JsonIgnore
	private Collection<Todo> todos;

	public TodoLog(Date logDate, String log) {
		this.logDate = logDate;
		this.log = log;
		this.todos = new ArrayList<>();
	}

}
