package com.jalizadeh.springboot.web.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


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
		super();
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

	public Long getId() {
		return id;
	}

	public String getUid() {
		return uid;
	}

	public Date getTest_date() {
		return test_date;
	}

	public User getUser() {
		return user;
	}

	public Long getBody_health() {
		return body_health;
	}

	public Long getMental_health() {
		return mental_health;
	}

	public Long getFinancial() {
		return financial;
	}

	public Long getBusiness() {
		return business;
	}

	public Long getLife_style() {
		return life_style;
	}

	public Long getSpiritual() {
		return spiritual;
	}

	public Long getFamily() {
		return family;
	}

	public Long getRelationship() {
		return relationship;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public void setTest_date(Date test_date) {
		this.test_date = test_date;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setBody_health(Long body_health) {
		this.body_health = body_health;
	}

	public void setMental_health(Long mental_health) {
		this.mental_health = mental_health;
	}

	public void setFinancial(Long financial) {
		this.financial = financial;
	}

	public void setBusiness(Long business) {
		this.business = business;
	}

	public void setLife_style(Long life_style) {
		this.life_style = life_style;
	}

	public void setSpiritual(Long spiritual) {
		this.spiritual = spiritual;
	}

	public void setFamily(Long family) {
		this.family = family;
	}

	public void setRelationship(Long relationship) {
		this.relationship = relationship;
	}

	

}
