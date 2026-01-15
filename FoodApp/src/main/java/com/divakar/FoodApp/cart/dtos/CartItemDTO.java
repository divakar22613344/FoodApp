package com.divakar.FoodApp.cart.dtos;

import java.math.BigDecimal;

import com.divakar.FoodApp.menu.dtos.MenuDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartItemDTO {

    private Long id;
    private MenuDTO menu;
    private int quantity;
    private BigDecimal pricePerUnit;
    private BigDecimal subTotal;
}
