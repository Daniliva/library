package com.example.demo.controller;

import com.example.demo.model.book.Book;
import com.example.demo.model.journals.JournalBook;
import com.example.demo.repo.JournalBookRepository;
import com.example.demo.repo.JournalUserRepository;
import com.example.demo.service.BookService;
import com.example.demo.service.JournalBookService;
import com.example.demo.service.JournalUserService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("JournalBook")
public class JournalBookController {
    @Autowired
    private UserService userService;
    @Autowired
    private JournalUserService journalUserService;
    @Autowired
    JournalUserRepository journalUserRepository;
    @Autowired
    private BookService bookService;
    @Autowired
    private JournalBookService journalBookService;
    @Autowired
    JournalBookRepository journalBookRepository;
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET)
    public JournalBook takeAInfoBook(@PathVariable("id")long bookId) {
        return journalBookRepository.getByBookId(bookService.getBookById(bookId).getBody());
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(value = "/popular", method = RequestMethod.GET)
    public List<Book> takeByMostPopularBook() {
        return journalBookRepository.getByMostPopularBook();
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(value = "/notpopular", method = RequestMethod.GET)
    public List<Book> takeByNotPopularBook() {
        return journalBookRepository.getByNotPopularBook();
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(value = "/popularauthor", method = RequestMethod.POST)
    public List<Book> takeByMostPopularBookAuthor(@RequestBody String author) {
        return journalBookRepository.getByMostPopularBookOfAuthor(author);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(value = "/notpopularauthor", method = RequestMethod.POST)
    public List<Book> takeByNotPopularBookAuthor(@RequestBody String author) {
        return journalBookRepository.getByNotPopularBookOfAuthor(author);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(value = "/populargenre", method = RequestMethod.POST)
    public List<Book> takeByMostPopularBookGenre(@RequestBody String genre) {
        return journalBookRepository.getByMostPopularBookOfGenre(genre);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(value = "/notpopulargenre", method = RequestMethod.POST)
    public List<Book> takeByNotPopularBookGenre(@RequestBody String genre) {
        return journalBookRepository.getByNotPopularBookOfGenre(genre);
    }
}
