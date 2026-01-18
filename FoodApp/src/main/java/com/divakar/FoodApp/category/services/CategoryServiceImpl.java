package com.divakar.FoodApp.category.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.divakar.FoodApp.category.dtos.CategoryDTO;
import com.divakar.FoodApp.category.entity.Category;
import com.divakar.FoodApp.category.repository.CategoryRepository;
import com.divakar.FoodApp.exceptions.NotFoundException;
import com.divakar.FoodApp.response.Response;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response<CategoryDTO> addCategory(CategoryDTO categoryDTO) {

        log.info("Inside addCategory()");
        Category category = modelMapper.map(categoryDTO, Category.class);

        categoryRepository.save(category);

        return Response.<CategoryDTO>builder().statusCode(HttpStatus.OK.value()).message("Category added successfully")
                .build();
    }

    @Override
    public Response<CategoryDTO> updateCategory(CategoryDTO categoryDTO) {
        log.info("Inside updateCategory()");
        Category category = categoryRepository.findById(categoryDTO.getId())
                .orElseThrow(() -> new NotFoundException("Category Not Found"));

        if (categoryDTO.getName() != null && !categoryDTO.getName().isEmpty())
            category.setName(categoryDTO.getName());
        if (categoryDTO.getDescription() != null)
            category.setDescription(categoryDTO.getDescription());

        return Response.<CategoryDTO>builder().statusCode(HttpStatus.OK.value())
                .message("Category Updated successfully").build();
    }

    @Override
    public Response<CategoryDTO> getCategoryById(Long id) {
        log.info("Inside getCategoryById()");

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category Not Found"));

        CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);

        return Response.<CategoryDTO>builder().statusCode(HttpStatus.OK.value())
                .message("Category By ID Retrieved successfully").data(categoryDTO).build();

    }

    @Override
    public Response<List<CategoryDTO>> getAllCategories() {
        log.info("Inside getAllCategories()");

        List<Category> categories = categoryRepository.findAll();

        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class)).toList();

        return Response.<List<CategoryDTO>>builder().statusCode(HttpStatus.OK.value())
                .message("ALL Category Retrieved successfully").data(categoryDTOS).build();
    }

    @Override
    public Response<?> deleteCategory(Long id) {
        log.info("Inside deleteCategory()");

        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException("Category Not Found");
        }

        categoryRepository.deleteById(id);

        return Response.builder().statusCode(HttpStatus.OK.value())
                .message("Category Deleted successfully").build();
    }

}
