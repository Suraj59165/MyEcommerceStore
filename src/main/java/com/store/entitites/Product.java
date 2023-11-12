package com.store.entitites;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    private String id;
    private String title;
    @Column(length = 10000)
    private String description;
    private double price;
    private int discountedPrice;
    private int quantity;
    private LocalDateTime addedDate;
    private boolean live;
    private boolean stock;
    private String productImageName;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;


}
