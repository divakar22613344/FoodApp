package com.divakar.FoodApp.cart.entity;

import java.math.BigDecimal;

import com.divakar.FoodApp.menu.entity.Menu;

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
@Table(name = "cart_items")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // many cart items can belong to one cart
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne // many cart items can refer to one menu item
    @JoinColumn(name = "menu_id")
    private Menu menu;

    
    private int quantity;
    private BigDecimal pricePerUnit;
    private BigDecimal subTotal;

}
