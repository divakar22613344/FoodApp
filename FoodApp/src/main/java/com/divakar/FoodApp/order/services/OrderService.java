package com.divakar.FoodApp.order.services;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;

import com.divakar.FoodApp.enums.OrderStatus;
import com.divakar.FoodApp.order.dtos.OrderDTO;
import com.divakar.FoodApp.order.dtos.OrderItemDTO;
import com.divakar.FoodApp.response.Response;

public interface OrderService {

    Response<?> placeOrderFromCart() throws BadRequestException;
    Response<OrderDTO> getOrderById(Long id);
    Response<Page<OrderDTO>> getAllOrders(OrderStatus orderStatus, int page, int size);
    Response<List<OrderDTO>> getOrdersOfUser();
    Response<OrderItemDTO> getOrderItemById(Long orderItemId);
    Response<OrderDTO> updateOrderStatus(OrderDTO orderDTO);
    Response<Long> countUniqueCustomers();
}