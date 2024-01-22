package com.jalizadeh.todocial.web.model.gym.shop;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class BillingAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String BillingAddressName;

	private String BillingAddressStreet1;

	private String BillingAddressStreet2;

	private String BillingAddressCity;

	private String BillingAddressState;

	private String BillingAddressCountry;

	private String BillingAddressZipcode;
	
	@OneToOne
	private Order order;

}
