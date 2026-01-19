package com.divakar.FoodApp.cart.services;

import com.divakar.FoodApp.cart.dtos.CartDTO;
import com.divakar.FoodApp.response.Response;

public interface CartService {
    Response<?> addItemToCart(CartDTO cartDTO);
    
    Response<?> incrementItem(Long menuId);
    
    Response<?> decrementItem(Long menuId);
    
    Response<?> removeItem(Long cartItemId);
    
    Response<CartDTO> getShoppingCart();
    
    Response<?> clearShoppingCart();
}