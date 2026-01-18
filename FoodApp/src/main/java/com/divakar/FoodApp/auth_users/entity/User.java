package com.divakar.FoodApp.auth_users.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.divakar.FoodApp.cart.entity.Cart;
import com.divakar.FoodApp.order.entity.Order;
import com.divakar.FoodApp.payment.entity.Payment;
import com.divakar.FoodApp.review.entity.Review;
import com.divakar.FoodApp.role.entity.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.JoinColumn;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;

    private String phoneNumber;

    private String profileUrl;

    private String address;

    private boolean isActive;

    @ManyToMany(fetch = FetchType.EAGER) // one user can have many roles, many roles can belong to many users
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) // one user can have many orders, one order belongs to one
                                                             // user,
    private List<Order> orders; // if user is deleted, all their orders are deleted too, if order is
                                // added/updated, user is not affected

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) // one user can have many reviews, one review belongs to
                                                             // one user
    private List<Review> reviews;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) // one user can have many payments, one payment belongs to
                                                             // one user
    private List<Payment> payments;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL) // one user has one cart, one cart belongs to one user
    private Cart cart;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
