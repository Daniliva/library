package com.example.demo.controller;


import com.example.demo.model.User;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.journals.JournalUser;
import com.example.demo.repo.JournalUserRepository;
import com.example.demo.service.JournalUserService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JournalUserService journalUserService;
    @Autowired
    JournalUserRepository journalUserRepository;
    //@Secured({"ROLE_ADMIN"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/users", method = RequestMethod.GET)
    public List<User> listUser(){
        return userService.findAll();
    }

    //@Secured("ROLE_USER")
    //@PreAuthorize("hasRole('ROLE_USER')")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public User getOne(@PathVariable(value = "id") Long id)
    {
        return userService.findById(id);
    }

    @RequestMapping(value="/signup", method = RequestMethod.POST)
    public User saveUser(@RequestBody UserDTO user){
        User saveUser= userService.save(user);
        JournalUser journalUser=new JournalUser();
        journalUserRepository.save(journalUser);
        journalUser.setUserId(saveUser);
        journalUserRepository.save(journalUser);
        return saveUser;
    }

}
