package com.divakar.FoodApp.cart.dtos;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartDTO {
    private Long id;
    private List<CartItemDTO> cartItems;
    private Long menuId;
    private int quantity;
    private BigDecimal totalAmount;
}
