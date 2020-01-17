package com.example.demo.controller;

import com.example.demo.dto.book.BookAnswerDTO;
import com.example.demo.dto.book.BookDTO;
import com.example.demo.model.autorization.User;
import com.example.demo.model.book.Book;
import com.example.demo.model.journals.JournalBook;
import com.example.demo.repository.book.BookRepository;
import com.example.demo.repository.journal.JournalBookRepository;
import com.example.demo.service.book.BookService;
import com.example.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private BookRepository bookRepository;
    ;

    @GetMapping
    public ResponseEntity<Set<Book>> list() {
        Set<Book> books1 = new HashSet<Book>(bookService.findAll());
        return ResponseEntity.ok().body(books1);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Book> createBook(@RequestBody BookDTO book) {
        Book b = bookService.save(book);
        JournalBook journalBook = new JournalBook();
        journalBookRepository.save(journalBook);
        journalBook.setBook(b);
        if (book.getCount() > 0 && book.getCount() != null) {
            journalBook.setCount(book.getCount());
        } else {
            journalBook.setCount((long) 1);
        }
        journalBookRepository.save(journalBook);
        return ResponseEntity.status(201).body(b);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<BookAnswerDTO>
    updateBook(@RequestBody BookDTO book, @PathVariable("id") Long bookId) {
        return bookService.update(book, bookId);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Book> deleteBook(@PathVariable("id") Long personId) {
        return bookService.delete(personId);
    }

    @RequestMapping(value = "/takeABook/{id}", method = RequestMethod.GET)
    public Boolean takeABook(@PathVariable("id") long userId, @RequestParam(value = "bookId") long bookId) {
        return bookService.takeABook(bookId, userId);
    }

    @RequestMapping(value = "/passBook/{id}", method = RequestMethod.GET)
    public Boolean passBook(@PathVariable("id") long userId, @RequestParam(value = "bookId") long bookId) {
        return bookService.passBook(bookId, userId);
    }

    @RequestMapping(value = "/takeAReservation/{id}", method = RequestMethod.GET)
    public Boolean takeAReservation(@PathVariable("id") long bookId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userService.findOne(userDetails.getUsername());
        return bookService.takeAReservation(bookId, user.getId());
    }

    @RequestMapping(value = "/passAReservation", method = RequestMethod.GET)
    public Boolean passAReservation(@RequestBody long bookId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userService.findOne(userDetails.getUsername());
        return bookService.passAReservation(bookId, user.getId());
    }

    @RequestMapping(value = "/genre", method = RequestMethod.POST)
    public ResponseEntity<List<Book>> listGenre(@RequestParam(value = "genre") String genre) {
        List<Book> books = bookRepository.getFindAllByGenre(genre);
        return ResponseEntity.ok().body(books);
    }

    @RequestMapping(value = "/name", method = RequestMethod.POST)
    public ResponseEntity<List<Book>> listName(@RequestParam(value = "name") String name) {
        List<Book> books = bookRepository.getFindAllByName(name);
        return ResponseEntity.ok().body(books);
    }

    @RequestMapping(value = "/author", method = RequestMethod.POST)
    public ResponseEntity<List<Book>> listAuthor(@RequestParam(value = "author") String author) {
        List<Book> books = bookRepository.getFindAllByAuthor(author);
        return ResponseEntity.ok().body(books);
    }

    @RequestMapping(value = "/author_and_genre", method = RequestMethod.POST)
    public ResponseEntity<List<Book>> listAuthorAndGenre(@RequestParam("author") String author, @RequestParam("genre") String genre) {
        List<Book> books = bookRepository.getFindAllByAuthorGenre(author, genre);
        return ResponseEntity.ok().body(books);
    }

}
