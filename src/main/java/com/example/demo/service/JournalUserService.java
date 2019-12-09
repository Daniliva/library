package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.model.book.Book;
import com.example.demo.model.journals.JournalBook;
import com.example.demo.model.journals.JournalUser;
import com.example.demo.repo.JournalUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "JournalUserService")
public class JournalUserService {
    @Autowired
    private JournalUserRepository journalUserRepository;

    public void save(JournalUser journalUser) {
        journalUserRepository.save(journalUser);
    }

    public JournalUser getByUserId(User user) {
        return journalUserRepository.getByUserId(user);
    }
}
