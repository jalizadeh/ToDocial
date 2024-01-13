package com.jalizadeh.todocial.web.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.Date;

@Entity
@Getter
@Setter
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
	
	private Date cancel_date;

	private boolean completed;
	
	private boolean canceled;

	@Getter(AccessLevel.NONE)
	private boolean isPublic;
	
	private Long like;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "todos_logs", joinColumns = @JoinColumn(name = "todo_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "log_id", referencedColumnName = "id"))
	private Collection<TodoLog> logs;

	public static enum TType {
		Fun, Learning, Improvement, Job, Book, Life
	}

	public static enum TPriority {
		Emergency, High, Medium, Low
	}
	
	public Todo() {
		this.creation_date = new Date();
		this.like = 0L;
	}

	public boolean getIsPublic() {
		return isPublic;
	}
}