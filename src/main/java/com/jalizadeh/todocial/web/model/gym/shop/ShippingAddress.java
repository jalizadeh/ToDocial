package com.jalizadeh.todocial.web.model.gym.shop;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ShippingAddress {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String ShippingAddressName;

	private String ShippingAddressStreet1;

	private String ShippingAddressStreet2;

	private String ShippingAddressCity;

	private String ShippingAddressState;

	private String ShippingAddressCountry;

	private String ShippingAddressZipcode;
	
	@OneToOne
	private Order order;

}
