package com.jalizadeh.todocial.web.model;

import lombok.Data;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

@Entity
@Data
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private String name;
	
	@OneToMany(fetch= FetchType.EAGER)
	@JoinTable(name="roles_privileges", 
		joinColumns = @JoinColumn(name="role_id", referencedColumnName="id"),
		inverseJoinColumns = @JoinColumn(name="privilege_id", referencedColumnName="id"))
	private Collection<Privilege> privileges;

}
