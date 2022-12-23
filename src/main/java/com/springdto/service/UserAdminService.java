package com.springdto.service;

import com.springdto.dto.LoginDTO;
import com.springdto.dto.UserDTO;
import com.springdto.model.User;
import com.springdto.repository.UserRepository;
import com.springdto.userException.Exception;
import com.springdto.utility.JWTToken;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserAdminService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private JWTToken jwtToken;

    public List<UserDTO> getAllUser(String Token) {
        LoginDTO userLoginDetails = jwtToken.decodeToken(Token);
        User user1 = userRepository
                .findByEmail(userLoginDetails.getEmail())
                .orElseThrow(() -> new Exception("User does not Exists."));
        if(user1.getLoggedStatus()) {
            if(!user1.getRole().equalsIgnoreCase("Admin")) {
                throw new Exception("Admin rights not assigned.");
            }
            List<UserDTO> list = userRepository.findAll().stream()
                    .map(user -> modelMapper.map(user, UserDTO.class))
                    .collect(Collectors.toList());
            if (list.size() == 0) {
                throw new Exception("No User exist in database.");
            }
            return list;
        }
        else {
            throw new Exception("Need to Login First.");
        }
    }

    public void deleteUsersByAdmin(String Token, String email) {
        LoginDTO userLoginDetails = jwtToken.decodeToken(Token);
        User userAdmin = userRepository
                .findByEmail(userLoginDetails.getEmail())
                .orElseThrow(() -> new Exception("User does not Exists."));
        if(userAdmin.getLoggedStatus()) {
            if(!userAdmin.getRole().equalsIgnoreCase("Admin")) {
                throw new Exception("Admin rights not assigned.");
            }
            User userToBeDeleted = userRepository
                    .findByEmail(email)
                    .orElseThrow(() -> new Exception("User does not Exists."));
            emailSenderService.sendEmailUpdate(userToBeDeleted.getEmail(),
                    "your Account has been Deleted by Admin.",
                    "Account Deleted.");
            emailSenderService.sendEmailUpdate(userAdmin.getEmail(),
                    "you have deleted User account : " + userToBeDeleted.getEmail(),
                    "Account Deleted.");
            userRepository.delete(userToBeDeleted);
        }
        else {
            throw new Exception("Admin rights not assigned.");
        }
    }

}
