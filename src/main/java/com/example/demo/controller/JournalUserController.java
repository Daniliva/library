package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.model.book.Book;
import com.example.demo.model.journals.JournalUser;
import com.example.demo.repo.JournalUserRepository;
import com.example.demo.service.JournalUserService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("JournalUser")
public class JournalUserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JournalUserService journalUserService;
    @Autowired
    JournalUserRepository journalUserRepository;

    @GetMapping
    public ResponseEntity<JournalUser> getJournalUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userService.findOne(userDetails.getUsername());
        journalUserService.getByUserId(user);
        return ResponseEntity.ok().body(journalUserService.getByUserId(user));

    }
    @RequestMapping(value = "/takeAll", method = RequestMethod.GET)
    public List<JournalUser> takeAReservation() {
        return journalUserRepository.findAll();
    }

}
