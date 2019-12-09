package com.example.demo.controller;

import com.example.demo.dto.BookDTO;
import com.example.demo.model.User;
import com.example.demo.model.book.Book;
import com.example.demo.model.journals.JournalBook;
import com.example.demo.repo.BookRepository;
import com.example.demo.repo.JournalBookRepository;
import com.example.demo.repo.UserRepository;
import com.example.demo.service.BookService;
import com.example.demo.service.JournalBookService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
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
    private JournalBookService journalBookService;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Set<Book>> list() {
        Set<Book> books = bookService.findAll();
        return ResponseEntity.ok().body(books);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(value = "/getBook/{id}", method = RequestMethod.GET)
    public ResponseEntity<Book> getBook(@PathVariable("id") long bookId) {
        return bookService.getBookById(bookId);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Book> createBook(@RequestBody BookDTO book) {
        /*   message.setCreationDate(LocalDateTime.now());*/
        Book b = bookService.save(book);
        JournalBook journalBook = new JournalBook();
        journalBookRepository.save(journalBook);
        journalBook.setBook(b);
        journalBook.setDateReservation(LocalDate.now().minusDays(1));
        journalBookRepository.save(journalBook);
        return ResponseEntity.status(201).body(b);
    }

    @PreAuthorize("hasAnyRole( 'ROLE_ADMIN')")
    @RequestMapping(value = "/create_mass/{count}", method = RequestMethod.POST)
    public ResponseEntity<Book> createMassBook(@RequestBody BookDTO book, @PathVariable("count") long count) {
        Book b = bookService.save(book);
        JournalBook journalBook = new JournalBook();
        journalBookRepository.save(journalBook);
        journalBook.setBook(b);
        journalBook.setDateReservation(LocalDate.now().minusDays(1));
        journalBookRepository.save(journalBook);
        for (int i = 1; i < count; i++) {
            createBook(book);
        }
        return ResponseEntity.status(201).body(b);
    }

    @PreAuthorize("hasAnyRole( 'ROLE_ADMIN')")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Book>
    updateBook(@RequestBody @Valid BookDTO book, @PathVariable("id") Long bookId) {
        return bookService.update(book, bookId);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Book> deleteBook(@PathVariable("id") Long personId) {
        return bookService.delete(personId);
    }

    @PreAuthorize("hasAnyRole( 'ROLE_ADMIN')")
    @RequestMapping(value = "/takeABook/{id}", method = RequestMethod.GET)
    public ResponseEntity<Book> takeABook(@PathVariable("id") long userId, @RequestBody long bookId) {
        return bookService.takeABook(bookId, userId);

    }

    @PreAuthorize("hasAnyRole( 'ROLE_ADMIN')")
    @RequestMapping(value = "/passBook/{id}", method = RequestMethod.GET)
    public ResponseEntity<Book> passBook(@PathVariable("id") long userId, @RequestBody long bookId) {
        return bookService.passBook(bookId, userId);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @RequestMapping(value = "/takeAReservation/{id}", method = RequestMethod.GET)
    public ResponseEntity<Book> takeAReservation(@PathVariable("id") long bookId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userService.findOne(userDetails.getUsername());
        return bookService.takeAReservation(bookId, user.getId());
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @RequestMapping(value = "/passAReservation", method = RequestMethod.GET)
    public ResponseEntity<Book> passAReservation(@RequestBody long bookId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userService.findOne(userDetails.getUsername());
        return bookService.passAReservation(bookId, user.getId());
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<String>> listGenre() {
        List<String> books = bookRepository.getFindAll(LocalDate.now());
        return ResponseEntity.ok().body(books);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(value = "/genre", method = RequestMethod.POST)
    public ResponseEntity<List<Book>> listGenre(@RequestBody String genre) {
        List<Book> books = bookRepository.getFindAllByDateAndGenre(LocalDate.now(), genre);
        return ResponseEntity.ok().body(books);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(value = "/author", method = RequestMethod.POST)
    public ResponseEntity<List<Book>> listAuthor(@RequestBody String author) {
        List<Book> books = bookRepository.getFindAllByDateAndAuthor(LocalDate.now(), author);
        return ResponseEntity.ok().body(books);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(value = "/author_and_genre", method = RequestMethod.POST)
    public ResponseEntity<List<Book>> listAuthorAndGenre(@RequestBody String author, @RequestBody String genre) {
        List<Book> books = bookRepository.getFindAllByDateAndAuthorGenre(LocalDate.now(), author,genre);
        return ResponseEntity.ok().body(books);
    }

}
