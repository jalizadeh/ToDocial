package com.jalizadeh.todocial.web.model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class PasswordResetToken {
	
	//12 hours in milliseconds
	private static final long EXPIRATION = 1000L * 60 * 60 * 12;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String token;
	
	@OneToOne(targetEntity=User.class , fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name="user_id")
	private User user;
	
	private Date expiryDate;
	
	private Date calculateExpiryDate(int expiryTimeInMinutes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Timestamp(cal.getTime().getTime()));
		cal.add(Calendar.MINUTE, expiryTimeInMinutes);
		return new Date(cal.getTime().getTime());
	}

	public PasswordResetToken() {
		super();
	}

	public PasswordResetToken(String token, User user, Date expiryDate) {
		this.token = token;
		this.user = user;
		
		//add 24 hours to the expiry date
		long sum = expiryDate.getTime() + EXPIRATION; 
		this.expiryDate = new Date(sum);
	}

	public Long getId() {
		return id;
	}

	public String getToken() {
		return token;
	}

	public User getUser() {
		return user;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	@Override
	public String toString() {
		return "PasswordResetToken [id=" + id + ", token=" + token + ", user=" + user + ", expiryDate=" + expiryDate
				+ "]";
	}
	
}
