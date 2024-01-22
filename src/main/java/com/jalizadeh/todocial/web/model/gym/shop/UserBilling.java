package com.jalizadeh.todocial.web.model.gym.shop;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class UserBilling {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String userBillingName;

	private String userBillingStreet1;

	private String userBillingStreet2;

	private String userBillingCity;

	private String userBillingState;

	private String userBillingCountry;

	private String userBillingZipcode;
	
	@OneToOne(cascade=CascadeType.ALL)
	private UserPayment userPayment;

}
