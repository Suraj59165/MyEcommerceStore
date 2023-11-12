package com.store.entitites;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class CartItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int quantity;
    private double totalPrice;
    @ManyToOne(fetch = FetchType.LAZY)
    private Cart cart;
    @OneToOne
    private Product product;

}
