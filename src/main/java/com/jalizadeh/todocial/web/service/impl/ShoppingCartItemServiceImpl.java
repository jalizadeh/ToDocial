package com.jalizadeh.todocial.web.service.impl;

import com.jalizadeh.todocial.web.model.User;
import com.jalizadeh.todocial.web.model.gym.GymPlan;
import com.jalizadeh.todocial.web.model.gym.shop.CartItem;
import com.jalizadeh.todocial.web.model.gym.shop.Order;
import com.jalizadeh.todocial.web.model.gym.shop.ShoppingCart;
import com.jalizadeh.todocial.web.repository.CartItemRepository;
import com.jalizadeh.todocial.web.service.ShoppingCartItemService;
import com.jalizadeh.todocial.web.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ShoppingCartItemServiceImpl implements ShoppingCartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public List<CartItem> findByShoppingCart(ShoppingCart shoppingCart) {
        return cartItemRepository.findByShoppingCart(shoppingCart);
    }

    @Override
    public CartItem updateCartItem(CartItem cartItem) {
        //TODO
        return null;
    }

    @Override
    public CartItem addToCartItem(GymPlan plan, User user) {
        //List<CartItem> cartItems = findByShoppingCart(user.getShoppingCart());
        

        return null;
    }

    @Override
    public CartItem findById(Long id) {
        return null;
    }

    @Override
    public void removeCartItem(CartItem cartItem) {

    }

    @Override
    public CartItem save(CartItem cartItem) {
        return null;
    }

    @Override
    public List<CartItem> findByOrder(Order order) {
        return null;
    }
}
