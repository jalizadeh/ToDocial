package com.jalizadeh.todocial.web.model.gym.shop;

import com.jalizadeh.todocial.web.model.gym.GymPlan;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Data
public class PlanToCartItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="plan_id")
	private GymPlan plan;
	
	@ManyToOne
	@JoinColumn(name="cart_item_id")
	private CartItem cartItem;

}
