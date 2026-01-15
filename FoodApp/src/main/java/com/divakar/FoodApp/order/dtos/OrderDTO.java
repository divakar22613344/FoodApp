package com.divakar.FoodApp.order.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.divakar.FoodApp.auth_users.dtos.UserDTO;
import com.divakar.FoodApp.enums.OrderStatus;
import com.divakar.FoodApp.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDTO {
    private Long id;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    private UserDTO user;
    private List<OrderItemDTO> orderItems;
}
