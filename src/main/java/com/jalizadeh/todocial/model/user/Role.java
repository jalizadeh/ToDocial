package com.jalizadeh.todocial.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false)
	private String name;
	
	//@OneToMany(fetch= FetchType.EAGER)
	@ManyToMany(fetch= FetchType.EAGER)
	@JoinTable(name="roles_privileges", 
		joinColumns = @JoinColumn(name="role_id", referencedColumnName="id"),
		inverseJoinColumns = @JoinColumn(name="privilege_id", referencedColumnName="id"))
	private Collection<Privilege> privileges;

	public Role(String name){
		this.name = name;
	}

}
