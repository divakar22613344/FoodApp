package com.divakar.FoodApp.order.controller;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.divakar.FoodApp.enums.OrderStatus;
import com.divakar.FoodApp.order.dtos.OrderDTO;
import com.divakar.FoodApp.order.dtos.OrderItemDTO;
import com.divakar.FoodApp.order.services.OrderService;
import com.divakar.FoodApp.response.Response;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/checkout")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<Response<?>> checkout() throws BadRequestException{
        return ResponseEntity.ok(orderService.placeOrderFromCart());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Response<OrderDTO>> getOrderById(@PathVariable Long id){
        return ResponseEntity.ok(orderService.getOrderById(id));
    }


    @GetMapping("/me")
    public ResponseEntity<Response<List<OrderDTO>>> getMyOrders() {
        return ResponseEntity.ok(orderService.getOrdersOfUser());
    }


    @GetMapping("order-item/{orderItemId}")
    public ResponseEntity<Response<OrderItemDTO>> getMyOrders(@PathVariable Long orderItemId) {
        return ResponseEntity.ok(orderService.getOrderItemById(orderItemId));
    }



    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<Page<OrderDTO>>> getAllOrders(
        @RequestParam(required = false) OrderStatus orderStatus,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "100") int size
    ) {
        return ResponseEntity.ok(orderService.getAllOrders(orderStatus,page,size));
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<OrderDTO>> updateOrderStatus(@RequestBody OrderDTO orderDTO){
        return ResponseEntity.ok(orderService.updateOrderStatus(orderDTO));
    }


    @GetMapping("/unique-customers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response<Long>> countUniqueCustomers() {
        return ResponseEntity.ok(orderService.countUniqueCustomers());
    }


}
