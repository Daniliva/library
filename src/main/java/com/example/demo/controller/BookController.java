package com.example.demo.controller;

import com.example.demo.dto.BookDTO;
import com.example.demo.model.User;
import com.example.demo.model.book.Book;
import com.example.demo.model.journals.JournalBook;
import com.example.demo.model.journals.JournalUser;
import com.example.demo.repo.BookRepository;
import com.example.demo.repo.JournalBookRepository;
import com.example.demo.service.BookService;
import com.example.demo.service.JournalBookService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("Book")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    @Autowired
    private JournalBookRepository journalBookRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private JournalBookService journalBookService;
    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public ResponseEntity<List<Book>> list() {
        List<Book> books = bookService.findAll();
        return ResponseEntity.ok().body(books);
    }

    @RequestMapping(value = "/getBook/{id}", method = RequestMethod.GET)
    public ResponseEntity<Book> getBook(@PathVariable("id") long bookId) {
        return bookService.getBookById(bookId);
    }


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Book> createBook(@RequestBody BookDTO book) {
        /*   message.setCreationDate(LocalDateTime.now());*/
        Book b = bookService.save(book);
        JournalBook journalBook=new JournalBook();
        journalBookRepository.save(journalBook);
        journalBook.setBook(b);
        journalBook.setDateReservation(LocalDate.now());
        journalBookRepository.save(journalBook);
        return ResponseEntity.status(201).body(b);
    }


    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Book>
    updateBook(@RequestBody @Valid BookDTO book, @PathVariable("id") Long bookId) {
        return bookService.update(book, bookId);
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Book> deleteBook(@PathVariable("id") Long personId) {
        return bookService.delete(personId);
    }


    @RequestMapping(value = "/takeABook/{id}", method = RequestMethod.GET)
    public ResponseEntity<Book> takeABook(@PathVariable("id") long userId, @RequestBody long bookId) {
        return bookService.takeABook(bookId, userId);

    }

    @RequestMapping(value = "/passBook/{id}", method = RequestMethod.GET)
    public ResponseEntity<Book> passBook(@PathVariable("id") long userId, @RequestBody long bookId) {
        return bookService.passBook(bookId, userId);
    }
    @RequestMapping(value = "/takeAReservation/{id}", method = RequestMethod.GET)
    public ResponseEntity<Book> takeAReservation(@PathVariable("id") long bookId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user =userService.findOne(userDetails.getUsername());
        return bookService.takeAReservation(bookId, user.getId());
    }

    @RequestMapping(value = "/passAReservation/{id}", method = RequestMethod.GET)
    public ResponseEntity<Book> passAReservation( @RequestBody long bookId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user =userService.findOne(userDetails.getUsername());
        return bookService.passAReservation(bookId,  user.getId());
    }
    @RequestMapping(value = "/genre/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Book>> listGenre(@RequestBody String bookGenre) {
        List<Book> books = bookService.findAll();
        return ResponseEntity.ok().body(books);
    }
}
