package com.jalizadeh.todocial.web.model.gym.shop;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jalizadeh.todocial.web.model.gym.GymPlan;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
public class CartItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private BigDecimal subtotal;
	
	@OneToOne
	private GymPlan plan;
	
	@OneToMany(mappedBy = "cartItem")
	@JsonIgnore
	private List<PlanToCartItem> planToCartItemList;
	
	@ManyToOne
	@JoinColumn(name="shopping_cart_id")
	private ShoppingCart shoppingCart;
	
	@ManyToOne
	@JoinColumn(name="order_id")
	private Order order;

}
