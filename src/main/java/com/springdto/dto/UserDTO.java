package com.springdto.dto;

import com.springdto.service.UserService;
import com.springdto.utility.GlobalResource;
import lombok.Data;
import org.slf4j.Logger;

@Data
public class UserDTO {
    private Long userId;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String about;
    private String role;
}
