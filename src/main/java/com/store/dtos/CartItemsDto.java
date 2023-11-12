package com.store.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CartItemsDto {
    private int id;
    private int quantity;
    private int totalPrice;
    private ProductDto productDto;
}
