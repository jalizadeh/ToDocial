package com.jalizadeh.todocial.web.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.jalizadeh.todocial.web.model.gym.shop.Order;
import com.jalizadeh.todocial.web.model.gym.shop.ShoppingCart;
import com.jalizadeh.todocial.web.model.gym.shop.UserPayment;
import com.jalizadeh.todocial.web.model.gym.shop.UserShipping;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.jalizadeh.todocial.system.service.UserService;
import com.jalizadeh.todocial.web.validator.PasswordMatches;
import com.jalizadeh.todocial.web.validator.UserValidator;
import com.jalizadeh.todocial.web.validator.ValidEmail;
import com.jalizadeh.todocial.web.validator.ValidPassword;

@Entity
@PasswordMatches
public class User implements UserDetails{
	
	@Id
	@Column(name="id", nullable = false, updatable = false)
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
	@Column(name="username", nullable = false, updatable = false)
	@Size(min=5, max=20, message="Username must be between 5-20 characters")
	private String username;
	
	/**
	 * Uniqueness is checked in {@link UserService}
	 */
	//@Column(unique=true) //handled while registration
	@ValidEmail
	@Column(name="email", nullable = false, updatable = false)
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
	
	private String photo;
	

	@ManyToMany(fetch=FetchType.EAGER) //when the object is created , I need the role
	@JoinTable(name="users_roles",
			joinColumns = @JoinColumn(name="user_id", referencedColumnName="id"),
			inverseJoinColumns = @JoinColumn(name="role_id", referencedColumnName="id"))
	private Collection<Role> roles;
	
	@OneToMany(fetch=FetchType.EAGER, //by default: FetchType.LAZY, I don't need to fetch all when object is created
			cascade=CascadeType.REMOVE)  
	@JoinTable(name="users_follows",
			joinColumns = @JoinColumn(name="followed"),
			inverseJoinColumns = @JoinColumn(name="follower", referencedColumnName="id"))
	private Collection<User> followers;
	
	@OneToMany(fetch=FetchType.EAGER, //by default: FetchType.LAZY, I don't need to fetch all when object is created
			cascade=CascadeType.REMOVE) 
	@JoinTable(name="users_follows",
			joinColumns = @JoinColumn(name="follower"),
			inverseJoinColumns = @JoinColumn(name="followed", referencedColumnName="id"))
	private Collection<User> followings;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
	private ShoppingCart shoppingCart;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private List<UserShipping> userShippingList;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private List<UserPayment> userPaymentList;

	@OneToMany(mappedBy = "user")
	private List<Order> orderList;

	
	public User() {	
		super();
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



	public String getPassword() {
		return password;
	}



	public String getMp() {
		return mp;
	}



	public boolean isEnabled() {
		return enabled;
	}



	public Collection<Role> getRoles() {
		return roles;
	}



	public Collection<User> getFollowers() {
		return followers;
	}



	public Collection<User> getFollowings() {
		return followings;
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



	public void setPassword(String password) {
		this.password = password;
	}



	public void setMp(String mp) {
		this.mp = mp;
	}



	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}



	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}



	public void setFollowers(Collection<User> followers) {
		this.followers = followers;
	}


	public void setFollowings(Collection<User> followings) {
		this.followings = followings;
	}
	

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
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

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public List<UserShipping> getUserShippingList() {
		return userShippingList;
	}

	public void setUserShippingList(List<UserShipping> userShippingList) {
		this.userShippingList = userShippingList;
	}

	public List<UserPayment> getUserPaymentList() {
		return userPaymentList;
	}

	public void setUserPaymentList(List<UserPayment> userPaymentList) {
		this.userPaymentList = userPaymentList;
	}

	public List<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles.stream()
				.flatMap(role -> Stream.concat(
						Stream.of(new SimpleGrantedAuthority(role.getName())), // Include role name
						role.getPrivileges().stream()
								.map(privilege -> new SimpleGrantedAuthority(privilege.getName()))
				)).collect(Collectors.toList());
	}

}
