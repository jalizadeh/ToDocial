package com.jalizadeh.springboot.web.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private String name;
	
	@ManyToMany(fetch= FetchType.EAGER)
	@JoinTable(name="roles_privileges", 
		joinColumns = @JoinColumn(name="role_id", referencedColumnName="id"),
		inverseJoinColumns = @JoinColumn(name="privilege_id", referencedColumnName="id"))
	private Collection<Privilege> privileges;


	public Role() {}
	

	public Role(String name) {
		this.name = name;
	}




	public Long getId() {
		return id;
	}


	public String getName() {
		return name;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Collection<Privilege> getPrivileges() {
		return privileges;
	}


	public void setPrivileges(Collection<Privilege> privileges) {
		this.privileges = privileges;
	}


	
}
