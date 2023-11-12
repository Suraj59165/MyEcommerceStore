package com.store.dtos;

import com.store.util.ValidImageName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String id;

    @Size(min = 3, max = 15, message = "Name should be 3 to 15 characters")
    @NotBlank(message = "Name can not be empty")
    private String name;

    @Email(message = "invalid user email")
    @NotBlank(message = "email can not be blank")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;


    @Size(min = 4, max = 6, message = "Invalid gender")
    private String gender;

    @NotBlank(message = "about is required")
    private String about;

    @ValidImageName
    private String image;


}
