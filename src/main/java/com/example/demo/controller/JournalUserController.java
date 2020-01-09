package com.example.demo.controller;

import com.example.demo.model.autorization.User;
import com.example.demo.model.journals.JournalUser;
import com.example.demo.repository.journal.JournalUserRepository;
import com.example.demo.service.journal.JournalUserService;
import com.example.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<JournalUser> getJournalUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userService.findOne(userDetails.getUsername());
        journalUserService.getByUserId(user);
        return ResponseEntity.ok().body(journalUserService.getByUserId(user));

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/takeAll", method = RequestMethod.GET)
    public List<JournalUser> takeAReservation() {
        return journalUserRepository.findAll();
    }

}
