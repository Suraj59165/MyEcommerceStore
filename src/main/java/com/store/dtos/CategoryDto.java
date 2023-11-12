package com.store.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CategoryDto {


    private String category_id;
    @NotBlank
    private String title;
    @NotBlank(message = "description is required")
    private String description;

    private String coverImage;

}
