package com.jalizadeh.todocial.web.model.gym.shop;

import com.jalizadeh.todocial.web.model.User;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class UserPayment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String type;

	private String cardName;

	private String cardNumber;

	private int expiryMonth;

	private int expiryYear;

	private int cvc;

	private String holderName;

	private boolean defaultPayment;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "userPayment",orphanRemoval = true)
	private UserBilling userBilling;

	
}
