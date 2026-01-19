package com.divakar.FoodApp.cart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divakar.FoodApp.cart.dtos.CartDTO;
import com.divakar.FoodApp.cart.services.CartService;
import com.divakar.FoodApp.response.Response;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/items")
    public ResponseEntity<Response<?>> addItemToCart(@RequestBody CartDTO cartDTO){
        return ResponseEntity.ok(cartService.addItemToCart(cartDTO));
    }


    @PutMapping("/items/increment/{menuId}")
    public ResponseEntity<Response<?>> incrementItem(@PathVariable Long menuId) {
        return ResponseEntity.ok(cartService.incrementItem(menuId));
    }

    @PutMapping("/items/decrement/{menuId}")
    public ResponseEntity<Response<?>> decrementItem(@PathVariable Long menuId) {
        return ResponseEntity.ok(cartService.decrementItem(menuId));
    }



    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<Response<?>> removeItem(@PathVariable Long cartItemId) {
        return ResponseEntity.ok(cartService.removeItem(cartItemId));
    }


    @GetMapping
    public ResponseEntity<Response<CartDTO>> getShoppingCart() {
        return ResponseEntity.ok(cartService.getShoppingCart());
    }


    @DeleteMapping
    public ResponseEntity<Response<?>> clearShoppingCart() {
        return ResponseEntity.ok(cartService.clearShoppingCart());
    }
}
