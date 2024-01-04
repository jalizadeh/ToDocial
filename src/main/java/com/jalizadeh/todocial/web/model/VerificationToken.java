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

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class VerificationToken {
	
	//24 hours in milliseconds
	private static final long EXPIRATION = 1000L * 60 * 60 * 24;
	
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

	public VerificationToken(String token, User user, Date expiryDate) {
		super();
		this.token = token;
		this.user = user;
		
		//add 24 hours to the expiry date
		long sum = expiryDate.getTime() + EXPIRATION; 
		this.expiryDate = new Date(sum);
	}

	@Override
	public String toString() {
		return "VerificationToken [id=" + id + ", token=" + token + ", user=" + user + ", expiryDate=" + expiryDate
				+ "]";
	}
	
}
