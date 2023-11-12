package com.store.repositories;

import com.store.entitites.Cart;
import com.store.entitites.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart, String> {
    Optional<Cart> findByUser(User user);
}
