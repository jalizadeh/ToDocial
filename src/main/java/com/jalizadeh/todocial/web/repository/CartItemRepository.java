package com.jalizadeh.todocial.web.repository;

import com.jalizadeh.todocial.web.model.gym.shop.CartItem;
import com.jalizadeh.todocial.web.model.gym.shop.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {


    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
}
