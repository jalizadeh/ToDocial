package com.jalizadeh.todocial.web.model.gym.shop;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Payment {

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
	
	@OneToOne
	private Order order;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "userPayment")
	private UserBilling userBilling;

}
