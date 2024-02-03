package com.jalizadeh.todocial.web.model;

import java.util.Collection;
import jakarta.persistence.*;

@Entity
public class Privilege {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	@ManyToMany(mappedBy="privileges")
	private Collection<Role> roles;

	public Privilege() {
		super();
	}

	public Privilege(String name) {
		super();
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
}
