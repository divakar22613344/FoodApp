package com.divakar.FoodApp.order.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.divakar.FoodApp.auth_users.entity.User;
import com.divakar.FoodApp.enums.OrderStatus;
import com.divakar.FoodApp.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByOrderStatus(OrderStatus orderStatus, Pageable pageable);

    List<Order> findByUserOrderByOrderDateDesc(User user);

    @Query("SELECT COUNT(DISTINCT o.user.id) FROM Order o")
    long countDistinctUsers();
}

