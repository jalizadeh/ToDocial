package com.jalizadeh.springboot.web.model;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.jalizadeh.springboot.web.service.UserService;
import com.jalizadeh.springboot.web.validator.PasswordMatches;
import com.jalizadeh.springboot.web.validator.UserValidator;
import com.jalizadeh.springboot.web.validator.ValidEmail;
import com.jalizadeh.springboot.web.validator.ValidPassword;

@Entity
@PasswordMatches
public class User implements UserDetails{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	/**
	 * validation is done in {@link UserValidator}
	 */
	private String firstname;
	
	/**
	 * validation is done in {@link UserValidator}
	 */
	private String lastname;
	
	/**
	 * Uniqueness is checked in {@link UserService}
	 */
	//@Column(unique=true)
	@Size(min=5, max=20, message="Username must be between 5-20 characters")
	private String username;
	
	/**
	 * Uniqueness is checked in {@link UserService}
	 */
	//@Column(unique=true) I handle this while registering
	@ValidEmail
	@NotNull
	@NotEmpty
	private String email;
	
	@ValidPassword
	@Column(length=100)
	private String password;
	
	//for now, disabled to support password change
	//@Transient //do not consider it inside my table 
	@NotEmpty(message="It must match your entered password")
	private String mp;

	
	@Column(nullable = false)
	private boolean enabled;
	

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="users_roles",
			joinColumns = @JoinColumn(name="user_id", referencedColumnName="id"),
			inverseJoinColumns = @JoinColumn(name="role_id", referencedColumnName="id"))
	private Collection<Role> roles;
	
	public User() {	
		super();
		//this.enabled = false;
	}
	
	
	
	public User(String firstname, String lastname,
			@Size(min = 5, max = 20, message = "Username must be between 5-20 characters") String username,
			@NotNull @NotEmpty String email, String password,
			@NotEmpty(message = "It must match your entered password") String mp, boolean enabled,
			Collection<Role> roles) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.email = email;
		this.password = password;
		this.mp = mp;
		this.enabled = enabled;
		this.roles = roles;
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

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getMp() {
		return mp;
	}

	public void setMp(String mp) {
		this.mp = mp;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles.stream()
			.flatMap(role -> role.getPrivileges().stream())
			.map(p -> new SimpleGrantedAuthority(p.getName()))
			.collect(Collectors.toList());
	}

	

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}



	@Override
	public boolean isAccountNonLocked() {
		return true;
	}



	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

}
