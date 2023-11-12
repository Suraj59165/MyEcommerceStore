package com.store.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CartDto {
    private String id;
    private LocalDateTime createdAt;
    private UserDto userDto;
    private List<CartItemsDto> cartItemsDto = new ArrayList<>();
}
