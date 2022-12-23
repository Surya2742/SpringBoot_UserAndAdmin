package com.springdto.component;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Component {
    @Bean
    public ModelMapper modelmapper() {
        return new ModelMapper();
    }
}
