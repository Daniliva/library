package com.example.demo.service;


import com.example.demo.model.User;
import com.example.demo.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService {

    User save(UserDTO user);
    List<User> findAll();
    void delete(long id);
    User findOne(String username);
    User findById(Long id);
    long lastId();
}
