package com.jalizadeh.todocial.web.service;


import com.jalizadeh.todocial.web.model.User;
import com.jalizadeh.todocial.web.model.gym.GymPlan;
import com.jalizadeh.todocial.web.model.gym.shop.CartItem;
import com.jalizadeh.todocial.web.model.gym.shop.Order;
import com.jalizadeh.todocial.web.model.gym.shop.ShoppingCart;

import java.util.List;

public interface ShoppingCartItemService {
	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
	
	CartItem updateCartItem(CartItem cartItem);
	
	CartItem addToCartItem(GymPlan plan, User user);
	
	CartItem findById(Long id);
	
	void removeCartItem(CartItem cartItem);
	
	CartItem save(CartItem cartItem);
	
	List<CartItem> findByOrder(Order order);
}
