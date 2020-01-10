package com.example.demo.service.mail;

import com.example.demo.model.autorization.User;
import com.example.demo.model.autorization.UserRegistration;
import com.example.demo.repository.user.UserRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MessageSendService {
    @Autowired
    private  UserRegistrationRepository userRegistrationRepository;
    @Autowired
    private  MailSender mailSender;

    public MessageSendService() {
    }

    public  void newTokenAnswer(String userName) {
        UserRegistration userRegistration = userRegistrationRepository.getByUsername(userName);
        userRegistration.setDateAnswer(LocalDate.now());
        userRegistrationRepository.save(userRegistration);
    }

    public  void sentMessageActivate(UserRegistration userRegistration, User newUser) {

        String message = String.format(
                "Hello, %s! \n" +
                        "Welcome to Sweater. Please, visit next link: http://localhost:9000/token/activate/%s",
                newUser.getUsername(),
                userRegistration.getToken()
        );
        mailSender.send(
                newUser.getUsername(),
                "Activation code",
                message);
    }

    public  void sentMessageDeactivate(UserRegistration userRegistration, User newUser) {
        String message = String.format(
                "Hello, %s! \n" +
                        "Welcome to Sweater. Please, visit next link: http://localhost:9000/token/deactivate/%s",
                newUser.getUsername(),
                userRegistration.getToken()
        );
        mailSender.send(newUser.getUsername(), "Activation code", message);
    }

    public  void sentMessageModification(UserRegistration userRegistration, User newUser) {
        String message = String.format(
                "Hello, %s! \n" +
                        "Welcome to Sweater. Please, visit next link: http://localhost:9000/modification/%s",
                newUser.getUsername(),
                userRegistration.getToken()
        );
        mailSender.send(newUser.getUsername(), "Activation code", message);
    }
}
