package com.divakar.FoodApp.payment.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.divakar.FoodApp.auth_users.dtos.UserDTO;
import com.divakar.FoodApp.enums.PaymentGateWay;
import com.divakar.FoodApp.enums.PaymentStatus;
import com.divakar.FoodApp.order.dtos.OrderDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentDTO {
    private Long id;
    private Long orderId;
    private BigDecimal amount;
    private PaymentStatus paymentStatus;
    private String transactionId;
    private PaymentGateWay paymentGateWay;
    private String failureReason;
    private boolean success;

    private LocalDateTime paymentDate;
    private OrderDTO order;
    private UserDTO user;
}
