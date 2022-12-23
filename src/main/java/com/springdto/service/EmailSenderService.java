package com.springdto.service;

import com.springdto.utility.GlobalResource;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    private Logger logger = GlobalResource.getLogger(UserService.class);
    @Autowired
    public JavaMailSender mailSender;

    public void sendEmailUpdate(String toEmail, String body, String subject) {
        logger.info("createUser requesting emailUpdate");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("boby7724284@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);
    }
}
