package com.example.demo.service;

import com.example.demo.model.book.Book;
import com.example.demo.model.journals.JournalBook;
import com.example.demo.repo.JournalBookRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class JournalBookService {
    @Autowired
    private JournalBookRepository journalBookRepository;

    public void save(Book book) {
        JournalBook journalBook = new JournalBook();
        journalBook.setBook(book);
        save(journalBook);
    }

    public void save(JournalBook journalBook) {
        journalBookRepository.save(journalBook);
    }

    public JournalBook getByBookId(Book book) {
        return journalBookRepository.getByBookId(book);
    }
}
