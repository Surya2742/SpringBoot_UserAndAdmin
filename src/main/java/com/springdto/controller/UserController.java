package com.springdto.controller;

import com.springdto.dto.LoginDTO;
import com.springdto.dto.UserDTO;
import com.springdto.dto.UserUpdateDTO;
import com.springdto.service.UserService;
import com.springdto.utility.GlobalResource;
import com.springdto.utility.JWTToken;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    private Logger logger = GlobalResource.getLogger(UserController.class);

    @Autowired
    private JWTToken jwtToken;

    @GetMapping("/helloworld")
    public void hello() {
        logger.info("Inside Controller.");
        logger.debug("into Controller Debug.");
        logger.error("into controller Error.");
        logger.warn("into Controller Warn.");
        logger.trace("into Controller trace.");
    }

    @PostMapping("/createUser")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO user) {
        logger.info("createUser in UserController");
        userService.createUser(user);
        return new ResponseEntity(Map.of("message", "User Created Successfully"), HttpStatus.CREATED);
    }

    @PutMapping("/updateUser/{Token}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserUpdateDTO user,@PathVariable String Token) {
        userService.updateUser(user, Token);
        return new ResponseEntity(Map.of("message", "User Updated Successfully"), HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{Token}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable String Token) {
        userService.deleteUser(Token);
        return new ResponseEntity(Map.of("message", "User Deleted Successfully"), HttpStatus.OK);
    }

    @GetMapping("/getUser/{Token}")
    public UserDTO getUser (@PathVariable String Token) {
        return userService.getUser(Token);
    }

    @GetMapping("/login")
    public String UserLogin(@RequestBody LoginDTO userLogin) {
        return userService.Login(userLogin);
    }

    @GetMapping("/logout{Token}")
    public ResponseEntity<?> UserLogOut (@PathVariable String Token){
        userService.LogOut(Token);
        return new ResponseEntity(Map.of("message", "User Logged Out Successfully"), HttpStatus.OK);
    }

    @GetMapping("/generateToken")
    public String generateToken(@RequestBody LoginDTO userLogin) throws Exception {
        return jwtToken.generateToken(userLogin);
    }

    @GetMapping("/decodeToken/{Token}")
    public LoginDTO decodeToken (@PathVariable String Token) {
        return jwtToken.decodeToken(Token);
    }

}
