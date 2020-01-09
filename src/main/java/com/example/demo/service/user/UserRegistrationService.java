package com.example.demo.service.user;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.autorization.AuthToken;
import com.example.demo.model.autorization.User;
import com.example.demo.model.autorization.UserRegistration;
import com.example.demo.repository.user.UserRegistrationRepository;
import com.example.demo.service.MessageSendService;
import com.example.demo.service.validator.StringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Service
public class UserRegistrationService {
    @Autowired
    public UserRegistrationRepository userRegistrationRepository;
    public void createUserRegistration(User newUser) {
        StringService stringService = new StringService();
        String token = stringService.md5Apache(newUser.getUsername() + LocalDate.now());
        UserRegistration userRegistration;
        userRegistration = new UserRegistration(LocalDate.now(), newUser, token);
        userRegistrationRepository.save(userRegistration);
        MessageSendService.sentMessageActivate(userRegistration, newUser);
    }
    public void modificationUserRegistration(String username) {
        StringService stringService = new StringService();
        String token = stringService.md5Apache(username + LocalDate.now());
        UserRegistration userRegistration = userRegistrationRepository.getByUsername(username);
        userRegistration.setToken(token);
        userRegistration.setDateAnswer(LocalDate.now());
        userRegistrationRepository.save(userRegistration);
        MessageSendService.sentMessageActivate(userRegistration, userRegistration.getUser());
    }
    public ResponseEntity<?> activationUser(String code) {
        UserRegistration userRegistration = userRegistrationRepository.getByToken(code);
        if (userRegistration == null) {
        return ResponseEntity.ok("You don't registration");
        } else if (LocalDate.now().isAfter(userRegistration.getDateAnswer().plusDays(5))) {
            MessageSendService.sentMessageActivate(userRegistration, userRegistration.getUser());
            modificationUserRegistration(userRegistration.getUser().getUsername());
            return ResponseEntity.ok("To late. Please check your email!");
        }
        userRegistration.setEmailReal(true);
        userRegistrationRepository.save(userRegistration);
        return ResponseEntity.ok(true);
    }

    public Boolean deactivationUser(String code) {
        UserRegistration userRegistration = userRegistrationRepository.getByToken(code);
        if (userRegistration == null) {
            return false;
        } else if (LocalDate.now().isAfter(userRegistration.getDateAnswer().plusDays(5))) {
            return false;
        }
        userRegistration.setEmailReal(false);
        userRegistrationRepository.save(userRegistration);
        return true;
    }

    public Boolean activationUsername(String name) {
        UserRegistration userRegistration = userRegistrationRepository.getByUsername(name);
        if (userRegistration == null) {
            return false;
        } else if (LocalDate.now().isAfter(userRegistration.getDateAnswer().plusDays(5))) {
            return false;
        }
        userRegistration.setEmailReal(true);
        userRegistrationRepository.save(userRegistration);
        return true;
    }

    public Boolean deactivationUsername(String name) {
        UserRegistration userRegistration = userRegistrationRepository.getByUsername(name);
        if (userRegistration == null) {
            return false;
        }
        userRegistration.setEmailReal(false);
        userRegistrationRepository.save(userRegistration);
        return true;
    }

    public User modificationUser(String code, UserDTO user) {
        UserRegistration userRegistration = userRegistrationRepository.getByToken(code);
        User modificationUser = userRegistration.getUser();
        modificationUser.setAge(user.getAge());
        modificationUser.setPassword(user.getPassword());
        modificationUser.setUsername(user.getUsername());
        modificationUser.setSalary(user.getSalary());
        return modificationUser;
    }
}
