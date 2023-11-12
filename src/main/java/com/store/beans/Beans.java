package com.store.beans;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Beans {

    @Bean
    public ModelMapper modelMapper() {
        System.out.println("creating model mapper object");
        return new ModelMapper();
    }

}
