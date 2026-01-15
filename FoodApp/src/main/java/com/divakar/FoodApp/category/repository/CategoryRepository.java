package com.divakar.FoodApp.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.divakar.FoodApp.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    

}
