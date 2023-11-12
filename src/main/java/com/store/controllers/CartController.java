package com.store.controllers;

import com.store.dtos.AddItemsToCartRequest;
import com.store.dtos.CartDto;
import com.store.payloads.ApiResponseMessage;
import com.store.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;


    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemsToCart(@RequestBody AddItemsToCartRequest addItemsToCartRequest, @PathVariable String userId) {
        return new ResponseEntity<>(cartService.addItemsToCart(userId, addItemsToCartRequest), HttpStatus.OK);
    }

    @DeleteMapping("/items/{itemId}/{userId}")
    public ResponseEntity<ApiResponseMessage> removeItemsFromCart(@PathVariable int itemId, @PathVariable String userId) {
        cartService.removeItemsFromCart(userId, itemId);
        ApiResponseMessage message = ApiResponseMessage.builder()
                .message("item is removed")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
        ApiResponseMessage message = ApiResponseMessage.builder()
                .message("not cart is blank")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCartByUser(@PathVariable String userId) {
        return new ResponseEntity<>(cartService.getCartByUSer(userId), HttpStatus.OK);
    }

}
