package com.divakar.FoodApp.order.dtos;

import java.math.BigDecimal;

import com.divakar.FoodApp.menu.dtos.MenuDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderItemDTO {
    private Long id;
    private int quantity;

    private Long menuId;

    private MenuDTO menu;
    private BigDecimal pricePerUnit;
    private BigDecimal subtotal;
}
