package com.divakar.FoodApp.menu.dtos;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.divakar.FoodApp.review.dtos.ReviewDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@JsonInclude
@JsonIgnoreProperties(ignoreUnknown = true)
public class MenuDTO {
    private Long id;

    @NotBlank(message = "Menu name cannot be blank")
    private String name;

    private String description;

    @NotNull(message = "Menu price cannot be null")
    @Positive(message = "Menu price must be positive")
    private BigDecimal price;

    private String imageUrl;

    @NotNull(message = "Category ID cannot be null")
    private Long categoryId;

    private MultipartFile imageFile;
    private List<ReviewDTO> reviews;

}
