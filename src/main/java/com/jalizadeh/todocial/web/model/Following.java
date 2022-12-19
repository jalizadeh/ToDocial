package com.jalizadeh.todocial.web.model;

import java.util.Collection;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * This entity represents users that a user is "following" or is "followed"  by.
 * <br>
 * Why? At first I used "User" entity for this feature, but every time I need to
 * fetch all the "follower" or "followings", all the data related to these users
 * are fetched too, which I don't really need them.
 * <br>
 * Now I just 
 */

//@Entity
public class Following {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String firstname;
	
	private String lastname;	
	
	private String username;
	
	private String email;
	
	@ManyToMany(mappedBy="followings")
	private Collection<User> owner;

	public Following() {
		super();
	}

	public Following(String firstname, String lastname, String username, String email, Collection<User> owner) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.email = email;
		this.owner = owner;
	}

	public Long getId() {
		return id;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public Collection<User> getOwner() {
		return owner;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setOwner(Collection<User> owner) {
		this.owner = owner;
	}

	
}
