package com.divakar.FoodApp.review.entity;

import java.time.LocalDateTime;

import com.divakar.FoodApp.auth_users.entity.User;
import com.divakar.FoodApp.menu.entity.Menu;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "reviews")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Integer rating; // from 1 to 10

    @Column(columnDefinition = "TEXT")
    private String comment;

    private LocalDateTime createdAt;


    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;





}
