package com.divakar.FoodApp.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.divakar.FoodApp.cart.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    

}
