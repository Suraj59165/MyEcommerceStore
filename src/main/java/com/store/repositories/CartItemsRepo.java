package com.store.repositories;

import com.store.entitites.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemsRepo extends JpaRepository<CartItems,Integer> {
}
