package com.jalizadeh.todocial.service.impl;

import com.jalizadeh.todocial.model.gym.GymPlan;
import com.jalizadeh.todocial.service.ShoppingCartService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private Map<Long, GymPlan> cart = new HashMap();

    @Override
    public void addItem(GymPlan plan) {
        if(!cart.containsKey(plan.getId())){
            cart.put(plan.getId(), plan);
        }
    }

    @Override
    public void removeItem(GymPlan plan) {
        if(cart.containsKey(plan.getId())){
            cart.remove(plan.getId());
        }
    }

    @Override
    public List<GymPlan> getItemsInCart() {
        return Collections.unmodifiableList(
                cart.values().stream().collect(Collectors.toList())
        );
    }

    @Override
    public void checkout() {
        getItemsInCart().stream()
                .forEach(i -> {

                });
    }

    @Override
    public BigDecimal getTotal() {
        return cart.values().stream()
                .mapToInt(GymPlan::getPrice)
                .mapToObj(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
