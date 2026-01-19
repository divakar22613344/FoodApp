package com.divakar.FoodApp.cart.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.divakar.FoodApp.auth_users.entity.User;
import com.divakar.FoodApp.auth_users.services.UserService;
import com.divakar.FoodApp.cart.dtos.CartDTO;
import com.divakar.FoodApp.cart.entity.Cart;
import com.divakar.FoodApp.cart.entity.CartItem;
import com.divakar.FoodApp.cart.repository.CartItemRepository;
import com.divakar.FoodApp.cart.repository.CartRepository;
import com.divakar.FoodApp.exceptions.NotFoundException;
import com.divakar.FoodApp.menu.entity.Menu;
import com.divakar.FoodApp.menu.repository.MenuRepository;
import com.divakar.FoodApp.response.Response;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final MenuRepository menuRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Override
    public Response<?> addItemToCart(CartDTO cartDTO) {

        log.info("Inside addItemToCart()");

        Long menuId = cartDTO.getMenuId();
        int quantity = cartDTO.getQuantity();

        User user = userService.getCurrentLoggedInUser();

        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new NotFoundException("Menu item Not Found"));

        Cart cart = cartRepository.findByUser_Id(user.getId()).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            newCart.setCartItems(new ArrayList<>());
            return cartRepository.save(newCart);
        });

        // Check if the item is already in the cart;
        Optional<CartItem> optionalCartItem = cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getMenu().getId().equals(menuId)).findFirst();

        // if present, increment item
        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setSubTotal(cartItem.getPricePerUnit().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            cartItemRepository.save(cartItem);
        } else {
            // if not present add it

            CartItem newCartItem = CartItem.builder().cart(cart).menu(menu).quantity(quantity)
                    .pricePerUnit(menu.getPrice()).subTotal(menu.getPrice().multiply(BigDecimal.valueOf(quantity)))
                    .build();

            cart.getCartItems().add(newCartItem);

            cartItemRepository.save(newCartItem);

        }

        //cartRepository.save(cart);

        return Response.builder().statusCode(HttpStatus.OK.value()).message("Item added to cart successfully").build();
    }

    @Override
    public Response<?> incrementItem(Long menuId) {
        log.info("Inside incrementItem()");
        User user = userService.getCurrentLoggedInUser();
        Cart cart = cartRepository.findByUser_Id(user.getId()).orElseThrow(() -> new NotFoundException("Cart Not Found"));

        CartItem cartItem = cart.getCartItems().stream().filter(item -> item.getMenu().getId().equals(menuId)).findFirst().orElseThrow(() -> new NotFoundException("Menu Not Found in Cart"));

        int newQuantity = cartItem.getQuantity() + 1;

        cartItem.setQuantity(newQuantity);
        cartItem.setSubTotal(cartItem.getPricePerUnit().multiply(BigDecimal.valueOf(newQuantity)));
        cartItemRepository.save(cartItem);

        return Response.builder().statusCode(HttpStatus.OK.value()).message("Item Incremented by 1 Successfully").build();
    }

    @Override
    public Response<?> decrementItem(Long menuId) {
        log.info("Inside decrementItem()");

        User user = userService.getCurrentLoggedInUser();
        Cart cart = cartRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> new NotFoundException("Cart Not Found"));

        CartItem cartItem = cart.getCartItems().stream().filter(item -> item.getMenu().getId().equals(menuId))
                .findFirst().orElseThrow(() -> new NotFoundException("Menu Not Found in Cart"));

        int newQuantity = cartItem.getQuantity() - 1;

        if(newQuantity > 0){
            cartItem.setQuantity(newQuantity);
            cartItem.setSubTotal(cartItem.getPricePerUnit().multiply(BigDecimal.valueOf(newQuantity)));
            cartItemRepository.save(cartItem);
        }else{
            cart.getCartItems().remove(cartItem);
            cartItemRepository.delete(cartItem);
        }

        return Response.builder().statusCode(HttpStatus.OK.value()).message("Item Decremented by 1 Successfully")
                .build();
    }

    @Override
    public Response<?> removeItem(Long cartItemId) {
        log.info("Inside removeItem()");

        User user = userService.getCurrentLoggedInUser();
        Cart cart = cartRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> new NotFoundException("Cart Not Found"));

        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new NotFoundException("Cart Item Not Found"));

        if(!cart.getCartItems().contains(cartItem)){
            throw new NotFoundException("Cart Item does not belong to this user's cart");
        }

        cart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);

        return Response.builder().statusCode(HttpStatus.OK.value()).message("Item Removed From Cart Successfully")
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Response<CartDTO> getShoppingCart() {
        log.info("Inside getShoppingCart()");

        User user = userService.getCurrentLoggedInUser();
        Cart cart = cartRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> new NotFoundException("Cart Not Found for user"));
        
        List<CartItem> cartItems = cart.getCartItems();

        CartDTO cartDTO = modelMapper.map(cart,CartDTO.class);

        BigDecimal totalAmount = BigDecimal.ZERO;
        if(cartItems != null){
            for(CartItem item: cartItems){
                totalAmount = totalAmount.add(item.getSubTotal());
            }
        }

        cartDTO.setTotalAmount(totalAmount);


        if(cartDTO.getCartItems() != null){
            cartDTO.getCartItems().forEach(item -> item.getMenu().setReviews(null));
        }


        return Response.<CartDTO>builder().statusCode(HttpStatus.OK.value()).message("Shopping Cart Retrieved Successfully").data(cartDTO)
                .build();
    }

    @Override
    public Response<?> clearShoppingCart() {
        log.info("Inside clearShoppingCart()");
        User user = userService.getCurrentLoggedInUser();
        Cart cart = cartRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> new NotFoundException("Cart Not Found for user"));

        
        cartItemRepository.deleteAll(cart.getCartItems());

        cart.getCartItems().clear();

        cartRepository.save(cart);

        return Response.builder().statusCode(HttpStatus.OK.value())
                .message("Shopping Cart Cleared Successfully")
                .build();
    }

}
