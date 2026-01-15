package com.divakar.FoodApp.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.divakar.FoodApp.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
