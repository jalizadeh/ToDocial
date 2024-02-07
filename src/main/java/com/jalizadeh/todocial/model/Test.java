package com.jalizadeh.todocial.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;


@Data
@Entity
public class Test {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(unique=true)
	private String uid;
	
	private Date test_date;
	
	@ManyToOne
	@JoinColumn(nullable=false, name="user_id")
	private User user;
	
	private Long body_health;
	private Long mental_health;
	private Long financial;
	private Long business;
	private Long life_style;
	private Long spiritual;
	private Long family; 			//family == relationship
	private Long relationship;

	public Test() {
		this.uid = UUID.randomUUID().toString();
		this.test_date = new Date();
		this.body_health = 0L;
		this.mental_health = 0L;
		this.financial = 0L;
		this.business = 0L;
		this.life_style = 0L;
		this.spiritual = 0L;
		this.family = 0L;
		this.relationship = 0L;
	}

}
