package com.store.services.implementations;

import com.store.dtos.AddItemsToCartRequest;
import com.store.dtos.CartDto;
import com.store.entitites.Cart;
import com.store.entitites.CartItems;
import com.store.entitites.Product;
import com.store.entitites.User;
import com.store.exceptions.BadApiRequest;
import com.store.exceptions.ResourceNotFoundException;
import com.store.repositories.CartItemsRepo;
import com.store.repositories.CartRepo;
import com.store.repositories.ProductRepo;
import com.store.repositories.UserRepo;
import com.store.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private CartItemsRepo cartItemsRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CartDto addItemsToCart(String userId, AddItemsToCartRequest addItemsToCartRequest) {
        int quantity = addItemsToCartRequest.getQuantity();
        String productId = addItemsToCartRequest.getProductId();
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product not fund with that given " + productId));
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found"));
        Cart cart = null;
        AtomicReference<Boolean> updated = new AtomicReference<Boolean>(false);
        try {
            cart = cartRepo.findByUser(user).get();
        } catch (NoSuchElementException exception) {
            cart = new Cart();
            cart.setId(UUID.randomUUID().toString()
            );
            cart.setCreatedAt(null);
        }
        //if user comes again and then we have to check their cart Items
        List<CartItems> items = cart.getCartItems();
        List<CartItems> updatedItems = items.stream().map(item -> {
            if (item.getProduct().getId().equals(productId)) {
                item.setQuantity((quantity));
                item.setTotalPrice(quantity * product.getPrice());
                updated.set(true);
            }
            return item;
        }).collect(Collectors.toList());
        cart.setCartItems(updatedItems);

        if (!updated.get()) {
            CartItems cartItems1 = CartItems.builder()
                    .quantity(quantity)
                    .totalPrice(quantity * product.getPrice())
                    .cart(cart)
                    .product(product)
                    .build();
            cart.getCartItems().add(cartItems1);
        }

        cart.setUser(user);
        return modelMapper.map(cartRepo.save(cart), CartDto.class);

    }

    @Override
    public void removeItemsFromCart(String userId, int cartItemId) {
        CartItems cartItems = cartItemsRepo.findById(cartItemId).orElseThrow(() -> new ResourceNotFoundException("cart items not found"));
        cartItemsRepo.delete(cartItems);

    }

    @Override
    public void clearCart(String userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user doesn't exists"));
        Cart cart = cartRepo.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("cart not found"));
        cart.getCartItems().clear();
        cartRepo.save(cart);


    }

    @Override
    public CartDto getCartByUSer(String userID) {
        User user = userRepo.findById(userID).orElseThrow(() -> new ResourceNotFoundException("user doesn't exists"));
        Cart cart = cartRepo.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("cart not found"));
        return modelMapper.map(cart,CartDto.class);

    }
}
