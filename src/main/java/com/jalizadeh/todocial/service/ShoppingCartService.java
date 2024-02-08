package com.jalizadeh.todocial.service;

import com.jalizadeh.todocial.model.gym.GymPlan;

import java.math.BigDecimal;
import java.util.List;

public interface ShoppingCartService {

    void addItem(GymPlan plan);

    void removeItem(GymPlan plan);

    List<GymPlan> getItemsInCart();

    void checkout();

    BigDecimal getTotal();
}
