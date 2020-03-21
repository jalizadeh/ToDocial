package com.jalizadeh.springboot.web.model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;


@Entity
public class Todo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Size(min=10, message="Enter at least 10 characters")
    private String desc;
    
    private Date creation_date;
    
    private Date due_date;
    
    private boolean completed;
    
    private boolean publicc;
    
    private Long like;

    @ManyToOne
	@JoinColumn(name="user_id")
    private User user;
	
    
	@ManyToMany(fetch = FetchType.LAZY,
            	cascade = CascadeType.ALL)
	@JoinTable(name="todos_logs", 
		joinColumns = @JoinColumn(name="todo_id", referencedColumnName="id"),
		inverseJoinColumns = @JoinColumn(name="log_id", referencedColumnName="id"))
	private Collection<TodoLog> logs;


	public Todo() {
		super();
	}


	public Todo(@Size(min = 10, message = "Enter at least 10 characters") String desc, Date creation_date,
			Date due_date, boolean completed, boolean publicc, Long like, User user, Collection<TodoLog> logs) {
		super();
		this.desc = desc;
		this.creation_date = creation_date;
		this.due_date = due_date;
		this.completed = completed;
		this.publicc = publicc;
		this.like = like;
		this.user = user;
		this.logs = logs;
	}


	public Long getId() {
		return id;
	}


	public String getDesc() {
		return desc;
	}


	public Date getCreation_date() {
		return creation_date;
	}


	public Date getDue_date() {
		return due_date;
	}


	public boolean isCompleted() {
		return completed;
	}


	public boolean isPublicc() {
		return publicc;
	}


	public Long getLike() {
		return like;
	}


	public User getUser() {
		return user;
	}


	public Collection<TodoLog> getLogs() {
		return logs;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}


	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}


	public void setDue_date(Date due_date) {
		this.due_date = due_date;
	}


	public void setCompleted(boolean completed) {
		this.completed = completed;
	}


	public void setPublicc(boolean publicc) {
		this.publicc = publicc;
	}


	public void setLike(Long like) {
		this.like = like;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public void setLogs(Collection<TodoLog> logs) {
		this.logs = logs;
	}


	

}