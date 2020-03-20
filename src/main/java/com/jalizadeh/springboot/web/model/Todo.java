package com.jalizadeh.springboot.web.model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
    
    @Column(name="targetdate")
    private Date targetDate;
    
    @Column(name="isdone", nullable=false)
    private boolean isDone;

	@ManyToOne
	@JoinColumn(name="user_id")
    private User user;
    
	@ManyToMany(fetch = FetchType.LAZY,
            	cascade = CascadeType.ALL)
	@JoinTable(name="todos_logs", 
		joinColumns = @JoinColumn(name="todo_id", referencedColumnName="id"),
		inverseJoinColumns = @JoinColumn(name="log_id", referencedColumnName="id"))
	private Collection<TodoLog> logs;
	
    public Todo() {}
    
    

    public Todo(@Size(min = 10, message = "Enter at least 10 characters") String desc, Date targetDate,
			boolean isDone, User user) {
		super();
		this.desc = desc;
		this.targetDate = targetDate;
		this.isDone = isDone;
		this.user = user;
	}


	public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (int) (prime * result + id);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Todo other = (Todo) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format(
                "Todo [id=%s, user=%s, desc=%s, targetDate=%s, isDone=%s]", id,
                user, desc, targetDate, isDone);
    }


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}



	public Collection<TodoLog> getLogs() {
		return logs;
	}



	public void setLogs(Collection<TodoLog> logs) {
		this.logs = logs;
	}

	
	
}