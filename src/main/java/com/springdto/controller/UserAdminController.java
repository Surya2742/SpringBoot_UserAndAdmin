package com.springdto.controller;

import com.springdto.dto.UserDTO;
import com.springdto.service.UserAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
public class UserAdminController {
    @Autowired
    private UserAdminService userAdminService;

    @GetMapping("/getAllUserByAdmin/{Token}")
    public List<UserDTO> getAllUser(@PathVariable String Token) {
        return userAdminService.getAllUser(Token);
    }

    @DeleteMapping("/deleteUserByAdmin/{Token}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable String Token, String email) {
        userAdminService.deleteUsersByAdmin(Token, email);
        return new ResponseEntity(Map.of("message", "User Deleted Successfully"), HttpStatus.OK);
    }
}
