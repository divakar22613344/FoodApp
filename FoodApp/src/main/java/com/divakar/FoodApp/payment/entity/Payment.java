package com.divakar.FoodApp.payment.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.divakar.FoodApp.auth_users.entity.User;
import com.divakar.FoodApp.enums.PaymentGateWay;
import com.divakar.FoodApp.enums.PaymentStatus;
import com.divakar.FoodApp.order.entity.Order;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "payments")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private String transactionId;
    @Enumerated(EnumType.STRING)
    private PaymentGateWay paymentGateWay;
    private String failureReason;
    private LocalDateTime paymentDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // the user who made the payment

}
