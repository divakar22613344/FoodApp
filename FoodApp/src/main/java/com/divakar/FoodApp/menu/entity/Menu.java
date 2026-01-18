package com.divakar.FoodApp.menu.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.divakar.FoodApp.category.entity.Category;
import com.divakar.FoodApp.order.entity.OrderItem;
import com.divakar.FoodApp.review.entity.Review;

@Entity
@Data
@Table(name = "menus")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long Id;

    public String name;
    public String description;

    private BigDecimal price;
    private String imageUrl;

    @ManyToOne(fetch = FetchType.EAGER) // Many food items can belong to one category
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL) // One menu item can have many order items
    private List<OrderItem> orderItems;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true) // One menu item can have many
                                                                                   // reviews
    private List<Review> reviews;

}
