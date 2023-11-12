package com.store.entitites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    private String id;

    @Column(name = "user_name")
    private String name;

    @Column(unique = true, name = "user_email")
    private String email;

    @Column(name = "user_password", length = 10)
    private String password;

    @Column(name = "user_gender")
    private String gender;

    @Column(length = 1000, name = "user_about")
    private String about;

    @Column(name = "user_image_name")
    private String image;

}
