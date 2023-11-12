package com.store.services;

import com.store.dtos.AddItemsToCartRequest;
import com.store.dtos.CartDto;

public interface CartService {

    CartDto addItemsToCart(String userId, AddItemsToCartRequest addItemsToCartRequest);

    void removeItemsFromCart(String userId, int cartItemID);

    void clearCart(String userId);

    CartDto getCartByUSer(String userID);
}
