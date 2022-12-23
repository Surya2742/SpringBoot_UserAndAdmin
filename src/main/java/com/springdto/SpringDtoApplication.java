package com.springdto;

import com.springdto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class SpringDtoApplication {

    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(SpringDtoApplication.class, args);
    }

}
