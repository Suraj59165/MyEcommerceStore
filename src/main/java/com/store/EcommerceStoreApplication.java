package com.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcommerceStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceStoreApplication.class, args);
        System.out.println("Ecommerce Store Started");
    }

}