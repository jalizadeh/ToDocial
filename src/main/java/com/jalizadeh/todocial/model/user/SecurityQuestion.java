package com.jalizadeh.todocial.model.user;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class SecurityQuestion {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long id;
	
	@OneToOne(targetEntity=User.class, fetch=FetchType.EAGER)
	@JoinColumn(nullable=false, name="user_id", unique=true)
	public User user;
	
	@OneToOne(targetEntity=SecurityQuestionDefinition.class, fetch=FetchType.EAGER)
	@JoinColumn(nullable=false, name="sqd_id", unique=true)
	public SecurityQuestionDefinition questionDefinition;
	
	
	public String answer;

	
	public SecurityQuestion() {
	}


	public SecurityQuestion(User user,SecurityQuestionDefinition sqd, String answer) {
		this.user = user;
		this.questionDefinition = sqd;
		this.answer = answer;
	}


	public Long getId() {
		return id;
	}


	public User getUser() {
		return user;
	}


	public SecurityQuestionDefinition getQuestionDefinition() {
		return questionDefinition;
	}


	public String getAnswer() {
		return answer;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public void setQuestionDefinition(SecurityQuestionDefinition questionDefinition) {
		this.questionDefinition = questionDefinition;
	}


	public void setAnswer(String answer) {
		this.answer = answer;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answer == null) ? 0 : answer.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((questionDefinition == null) ? 0 : questionDefinition.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SecurityQuestion other = (SecurityQuestion) obj;
		if (answer == null) {
			if (other.answer != null)
				return false;
		} else if (!answer.equals(other.answer))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (questionDefinition == null) {
			if (other.questionDefinition != null)
				return false;
		} else if (!questionDefinition.equals(other.questionDefinition))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
	
}
