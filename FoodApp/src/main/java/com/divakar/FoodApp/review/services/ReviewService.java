package com.divakar.FoodApp.review.services;

import java.util.List;

import org.apache.coyote.BadRequestException;

import com.divakar.FoodApp.response.Response;
import com.divakar.FoodApp.review.dtos.ReviewDTO;

public interface ReviewService {
    Response<ReviewDTO> createReview(ReviewDTO reviewDTO) throws BadRequestException;
    
    Response<List<ReviewDTO>> getReviewsForMenu(Long menuId);
    
    Response<Double> getAverageRating(Long menuId);

    

}
