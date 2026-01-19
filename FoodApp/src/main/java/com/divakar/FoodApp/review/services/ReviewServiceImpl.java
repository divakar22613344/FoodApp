package com.divakar.FoodApp.review.services;

import java.time.LocalDateTime;
import java.util.List;

import javax.ws.rs.NotFoundException;

import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.divakar.FoodApp.auth_users.entity.User;
import com.divakar.FoodApp.auth_users.services.UserService;
import com.divakar.FoodApp.enums.OrderStatus;
import com.divakar.FoodApp.menu.entity.Menu;
import com.divakar.FoodApp.menu.repository.MenuRepository;
import com.divakar.FoodApp.order.entity.Order;
import com.divakar.FoodApp.order.repository.OrderItemRepository;
import com.divakar.FoodApp.order.repository.OrderRepository;
import com.divakar.FoodApp.response.Response;
import com.divakar.FoodApp.review.dtos.ReviewDTO;
import com.divakar.FoodApp.review.entity.Review;
import com.divakar.FoodApp.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    

    @Override
    public Response<ReviewDTO> createReview(ReviewDTO reviewDTO) throws BadRequestException {
        log.info("Inside createReview()");

        User user = userService.getCurrentLoggedInUser();


        if(reviewDTO.getOrderId() == null || reviewDTO.getMenuId()==null){
            throw new BadRequestException("Order ID and Menu Item ID are required");
        }

        //Validate Menu Item exists
        Menu menu = menuRepository.findById(reviewDTO.getOrderId()).orElseThrow(() -> new NotFoundException("Order Not Found"));
        
        //Validate Order exits
        Order order = orderRepository.findById(reviewDTO.getOrderId()).orElseThrow(() -> new NotFoundException("Order Not Found"));


        //Make sure the Order belongs to the User
        if(!order.getUser().getId().equals(user.getId())){
            throw new BadRequestException("This Order Doesn't Belong to You");
        }

        //Validate order status is DELIVERED
        if(order.getOrderStatus() != OrderStatus.DELIVERED){
            throw new BadRequestException("You can only review items for DELIVEREDS orders");
        }


        //Validate that menu item was a part of this order
        boolean itemInOrder = orderItemRepository.existsByOrderIdAndMenuId(reviewDTO.getOrderId(), reviewDTO.getMenuId());

        if(!itemInOrder){
            throw new BadRequestException("This menu item was not a part of the specified order");
        }

        //Check if User already wrote a review for the item,
        if(reviewRepository.existsByUserIdAndMenuIdAndOrderId(user.getId(), reviewDTO.getMenuId(), reviewDTO.getOrderId())){
            throw new BadRequestException("You have already reviewed this item from this order");
        }

        Review review = Review.builder().user(user).menu(menu).orderId(reviewDTO.getOrderId()).
                        rating(reviewDTO.getRating()).comment(reviewDTO.getComment()).createdAt(LocalDateTime.now()).build();

        
        Review savedReview = reviewRepository.save(review);

        ReviewDTO responseDto = modelMapper.map(savedReview,ReviewDTO.class);
        responseDto.setUserName(user.getName());
        responseDto.setMenuName(menu.getName());

        return Response.<ReviewDTO>builder().statusCode(HttpStatus.OK.value()).message("Review addedd successfully").data(
                responseDto).build();
    }

    @Override
    public Response<List<ReviewDTO>> getReviewsForMenu(Long menuId) {

        log.info("Inside getReviewsForMenu()");

        List<Review> reviews = reviewRepository.findByMenuIdOrderByIdDesc(menuId);

        List<ReviewDTO> reviewDTOs = reviews.stream().map(review -> modelMapper.map(review,ReviewDTO.class)).toList();

        return Response.<List<ReviewDTO>>builder().statusCode(HttpStatus.OK.value()).message("Reviews retrieved successfully").data(reviewDTOs).build();
    }

    @Override
    public Response<Double> getAverageRating(Long menuId) {
        log.info("Inside getAverageRating()");

        Double averageRating = reviewRepository.calculateAverageRatingByMenuId(menuId);

        return Response.<Double>builder().statusCode(HttpStatus.OK.value())
                .message("Reviews retrieved successfully").data(averageRating!=null ? averageRating : 0.0).build();
    }

}
