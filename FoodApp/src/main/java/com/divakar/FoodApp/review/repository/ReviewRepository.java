package com.divakar.FoodApp.review.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.divakar.FoodApp.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByMenuIdOrderByIdDesc(Long menuId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.menu.id = :menuID")
    Double calculateAverageRatingByMenuId(@Param("menuID") Long menuId);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Review r WHERE r.user.id = :userID AND r.menu.id = :menuID AND r.orderId = :orderID")
    Boolean existsByUserIdAndMenuIdAndOrderId(@Param("userID") Long userId, @Param("menuID") Long menuId, @Param("orderID") Long orderId);
}
