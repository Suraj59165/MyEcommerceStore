package com.store.dtos;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductDto {
    private String id;
    private String title;
    private String description;
    private double price;
    private int discountedPrice;
    private int quantity;
    private LocalDateTime addedDate;
    private boolean live;
    private boolean stock;
    private String productImageName;
    private CategoryDto category;

}
