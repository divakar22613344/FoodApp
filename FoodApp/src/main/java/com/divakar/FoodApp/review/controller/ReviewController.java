package com.divakar.FoodApp.review.controller;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.divakar.FoodApp.response.Response;
import com.divakar.FoodApp.review.dtos.ReviewDTO;
import com.divakar.FoodApp.review.services.ReviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")

public class ReviewController {
    private final ReviewService reviewService;


    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Response<ReviewDTO>> createReview(
        @RequestBody @Valid ReviewDTO reviewDTO
    ) throws BadRequestException{
        return ResponseEntity.ok(reviewService.createReview(reviewDTO));
    }



    @GetMapping("/menu-item/{menuId}")
    public ResponseEntity<Response<List<ReviewDTO>>> getReviewsForMenu(
            @PathVariable Long menuId) {
        return ResponseEntity.ok(reviewService.getReviewsForMenu(menuId));
    }


    @GetMapping("/menu-item/average/{menuId}")
    public ResponseEntity<Response<Double>> getAverageRating(
            @PathVariable Long menuId) {
        return ResponseEntity.ok(reviewService.getAverageRating(menuId));
    }

}
