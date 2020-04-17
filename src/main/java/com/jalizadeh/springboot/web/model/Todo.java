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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
public class Todo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotEmpty(message = "Name can not be empty")
	private String name;

	private String description;

	private String reason;

	private String completion_note;

	private TType ttype;

	private TPriority priority;

	private Date creation_date;

	private Date target_date;

	private Date completion_date;

	private boolean completed;

	private boolean publicc;
	
	private Long like;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "todos_logs", joinColumns = @JoinColumn(name = "todo_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "log_id", referencedColumnName = "id"))
	private Collection<TodoLog> logs;

	public static enum TType {
		Fun, Learning, Improvement, Job, Book
	}

	public static enum TPriority {
		Emergency, High, Medium, Low
	}
	
	public Todo() {
		super();
		this.creation_date = new Date();
		this.like = 0L;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getReason() {
		return reason;
	}

	public String getCompletion_note() {
		return completion_note;
	}

	public TType getTtype() {
		return ttype;
	}

	public TPriority getPriority() {
		return priority;
	}

	public Date getCreation_date() {
		return creation_date;
	}

	public Date getTarget_date() {
		return target_date;
	}

	public Date getCompletion_date() {
		return completion_date;
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

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setCompletion_note(String completion_note) {
		this.completion_note = completion_note;
	}

	public void setTtype(TType ttype) {
		this.ttype = ttype;
	}

	public void setPriority(TPriority priority) {
		this.priority = priority;
	}

	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}

	public void setTarget_date(Date target_date) {
		this.target_date = target_date;
	}

	public void setCompletion_date(Date completion_date) {
		this.completion_date = completion_date;
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