package com.divakar.FoodApp.payment.controller;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divakar.FoodApp.payment.dtos.PaymentDTO;
import com.divakar.FoodApp.payment.services.PaymentService;
import com.divakar.FoodApp.response.Response;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/pay")
    public ResponseEntity<Response<?>> intitializePayment(@RequestBody @Valid PaymentDTO paymentRequest) throws BadRequestException{
        return ResponseEntity.ok(paymentService.initializePayment(paymentRequest));
    }



    @PostMapping("/update")
    public void updatePaymentForOrder(@RequestBody PaymentDTO paymentRequest)
            throws BadRequestException {
        paymentService.updatePaymentForOrder(paymentRequest);
    }


    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<List<PaymentDTO>>> getAllPayments()
            throws BadRequestException {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<Response<PaymentDTO>> getPaymentById(@PathVariable Long paymentId)
            throws BadRequestException {
        return ResponseEntity.ok(paymentService.getPaymentById(paymentId));
    }

}
