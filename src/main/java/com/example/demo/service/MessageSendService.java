package com.example.demo.service;

import com.example.demo.model.autorization.User;
import com.example.demo.model.autorization.UserRegistration;
import com.example.demo.repository.user.UserRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Service
public class MessageSendService {
    @Autowired
    private static UserRegistrationRepository userRegistrationRepository;
    @Autowired
    private static MailSender mailSender;

    public static void newTokenAnswer(String userName) {
        UserRegistration userRegistration = userRegistrationRepository.getByUsername(userName);
        userRegistration.setDateAnswer(LocalDate.now());
        userRegistrationRepository.save(userRegistration);
    }

    public static void sentMessageActivate(UserRegistration userRegistration, User newUser) {
        String message = String.format(
                "Hello, %s! \n" +
                        "Welcome to Sweater. Please, visit next link: http://localhost:9000/activate/%s",
                newUser.getUsername(),
                userRegistration.getToken()
        );
        mailSender.send(newUser.getUsername(), "Activation code", message);
    }//modification

    public static void sentMessageDeactivate(UserRegistration userRegistration, User newUser) {
        String message = String.format(
                "Hello, %s! \n" +
                        "Welcome to Sweater. Please, visit next link: http://localhost:9000/deactivate/%s",
                newUser.getUsername(),
                userRegistration.getToken()
        );
        mailSender.send(newUser.getUsername(), "Activation code", message);
    }

    public static void sentMessageModification(UserRegistration userRegistration, User newUser) {
        String message = String.format(
                "Hello, %s! \n" +
                        "Welcome to Sweater. Please, visit next link: http://localhost:9000/modification/%s",
                newUser.getUsername(),
                userRegistration.getToken()
        );
        mailSender.send(newUser.getUsername(), "Activation code", message);
    }
}
