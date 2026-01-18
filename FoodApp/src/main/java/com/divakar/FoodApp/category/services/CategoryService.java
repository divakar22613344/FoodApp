package com.divakar.FoodApp.category.services;

import java.util.List;

import com.divakar.FoodApp.category.dtos.CategoryDTO;
import com.divakar.FoodApp.response.Response;

public interface CategoryService {
    Response<CategoryDTO> addCategory(CategoryDTO categoryDTO);

    Response<CategoryDTO> updateCategory(CategoryDTO categoryDTO);

    Response<CategoryDTO> getCategoryById(Long id);

    Response<List<CategoryDTO>> getAllCategories();

    Response<?> deleteCategory(Long id);
}
