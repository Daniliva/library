package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.model.book.Book;
import com.example.demo.model.journals.JournalBook;
import com.example.demo.model.journals.JournalUser;
import com.example.demo.repo.JournalUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class JournalUserService {
    @Autowired
    private JournalUserRepository journalUserRepository;

    public void save(User newUser) {
        JournalUser journalUser = new JournalUser();
        journalUser.setUserId(newUser);
        journalUserRepository.save(journalUser);
    }
    public JournalUser getByUserId(User user) {
        return journalUserRepository.getByUserId(user);
    }
}
