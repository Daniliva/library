package com.example.demo.controller;

import com.example.demo.dto.book.BookAnswerDTO;
import com.example.demo.repository.journal.JournalBookRepository;
import com.example.demo.repository.journal.JournalUserRepository;
import com.example.demo.service.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("JournalBook")
public class JournalBookController {

    @Autowired
    JournalUserRepository journalUserRepository;
    @Autowired
    private BookService bookService;
    @Autowired
    JournalBookRepository journalBookRepository;
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public ResponseEntity<BookAnswerDTO> takeAInfoBook(@PathVariable("id")long bookId) {
        return bookService.getBookById(bookId);
    }
}
