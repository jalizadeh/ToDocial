package com.jalizadeh.springboot.web.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
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
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="role_id")
	private Role role;

	public User() {	
		//super();
		//this.enabled = false;
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

	public Role getRole() {
		return role;
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

	public void setRole(Role role) {
		this.role = role;
	}
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(role.getName()));
		return authorities;
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

	@Override
	public String toString() {
		return "User [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", username=" + username
				+ ", password=" + password + ", mp=" + mp + ", email=" + email
				+ ", enabled=" + enabled + ", role=" + role + "]";
	}
}
