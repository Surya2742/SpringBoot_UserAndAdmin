package com.springdto.service;

import com.springdto.dto.LoginDTO;
import com.springdto.dto.UserDTO;
import com.springdto.dto.UserUpdateDTO;
import com.springdto.model.User;
import com.springdto.repository.UserRepository;
import com.springdto.userException.Exception;
import com.springdto.utility.GlobalResource;
import com.springdto.utility.JWTToken;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private Logger logger = GlobalResource.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private JWTToken jwtToken;
    public void createUser(UserDTO userDTO) {
        logger.info("createUser in UserService.");
        if(userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new Exception("Email already exists");
        }
        emailSenderService.sendEmailUpdate(userDTO.getEmail(), "User account created Successfully.", "Account Created.");
        userRepository.save(modelMapper.map(userDTO, User.class));
    }

    public String Login(LoginDTO loginData) {
        String SECRET_KEY = "login";
        Map<String, Object> claims = new HashMap<>();
        User user = this.userRepository
                .findByEmailAndPassword(loginData.getEmail(), loginData.getPassword())
                .orElseThrow(() -> new Exception("Invalid Credentials"));
        if(!user.getLoggedStatus()) {
            user.setLoggedStatus(Boolean.TRUE);
            userRepository.save(user);
            claims.put("eMail", loginData.getEmail());
            claims.put("Password", loginData.getPassword());
            return Jwts.builder().setClaims(claims)
                    .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
        }
        else {
            throw new Exception("User Already Logged In.");
        }
    }

    public void LogOut(String Token) {
        LoginDTO userLoginDetails = jwtToken.decodeToken(Token);
        User user = this.userRepository
                .findByEmail(userLoginDetails.getEmail())
                .orElseThrow(() -> new Exception("User not found."));
        if(user.getLoggedStatus()) {
            user.setLoggedStatus(Boolean.FALSE);
            userRepository.save(user);
        }
        else {
            throw new Exception("Need to Log in first.");
        }
    }

    public void updateUser(UserUpdateDTO userUpdateDTO, String Token) {
        User user = userRepository
                .findByEmail(jwtToken.decodeToken(Token).getEmail())
                .orElseThrow(() -> new Exception("User does not Exists."));
        if(user.getLoggedStatus()) {
            if (!userUpdateDTO.getFirstName().isEmpty()) {
                user.setFirstName(userUpdateDTO.getFirstName());
            }
            if (!userUpdateDTO.getLastName().isEmpty()) {
                user.setLastName(userUpdateDTO.getLastName());
            }
            if (!userUpdateDTO.getAbout().isEmpty()) {
                user.setAbout(userUpdateDTO.getAbout());
            }
            userRepository.save(user);
            emailSenderService.sendEmailUpdate(user.getEmail(),
                    "User details Updated Successfully.\n" + user,
                    "AccountDetails Updated.");
        }
        else {
            throw new Exception("User Not Logged In.");
        }
    }
    public void deleteUser(String Token) {
        LoginDTO userLoginDetails = jwtToken.decodeToken(Token);
        User userToBeDeleted = userRepository
                .findByEmail(userLoginDetails.getEmail())
                .orElseThrow(() -> new Exception("User does not Exists."));
        if(userToBeDeleted.getLoggedStatus()) {
            emailSenderService.sendEmailUpdate(userToBeDeleted.getEmail(),
                    "User Account Deleted.\n" + userToBeDeleted,
                    "Account Deleted.");
            userRepository.delete(userToBeDeleted);
        }
        else {
            throw new Exception("User not Logged in.");
        }
    }

    public UserDTO getUser(String Token) {
        User user = userRepository
                .findByEmail(jwtToken.decodeToken(Token).getEmail())
                .orElseThrow(() -> new Exception("User does not Exists."));
        if(user.getLoggedStatus()) {
            return modelMapper.map(user, UserDTO.class);
        }
        else {
            throw new Exception("User need to Log in.");
        }
    }

}
