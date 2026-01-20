package com.divakar.FoodApp.payment.services;

import java.util.List;

import org.apache.coyote.BadRequestException;

import com.divakar.FoodApp.payment.dtos.PaymentDTO;
import com.divakar.FoodApp.response.Response;

public interface PaymentService {

    Response<?> initializePayment(PaymentDTO paymentRequest) throws BadRequestException;
    
    void updatePaymentForOrder(PaymentDTO paymentDTO);

    Response<List<PaymentDTO>> getAllPayments();
    Response<PaymentDTO> getPaymentById(Long paymentId);
}
