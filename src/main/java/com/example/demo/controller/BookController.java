package com.example.demo.controller;

import com.example.demo.dto.book.BookDTO;
import com.example.demo.model.autorization.User;
import com.example.demo.model.book.Book;
import com.example.demo.model.journals.JournalBook;
import com.example.demo.repository.book.BookRepository;
import com.example.demo.repository.journal.JournalBookRepository;
import com.example.demo.repository.user.UserRepository;
import com.example.demo.service.book.BookService;
import com.example.demo.service.journal.JournalBookService;
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
    private JournalBookService journalBookService;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Set<Book>> list() {
        Set<Book> books1 = new HashSet<Book>(bookService.findAll());
        return ResponseEntity.ok().body(books1);
    }

    /*  @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
      @RequestMapping(value = "/getBook/{id}", method = RequestMethod.GET)
      public ResponseEntity<Book> getBook(@PathVariable("id") long bookId) {
          return bookService.getBookById(bookId);
      }
  */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
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


   /* @PreAuthorize("hasAnyRole( 'ROLE_ADMIN')")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Book>
    updateBook(@RequestBody @Valid BookDTO book, @PathVariable("id") Long bookId) {
        return bookService.update(book, bookId);
    }*/

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Book> deleteBook(@PathVariable("id") Long personId) {
        return bookService.delete(personId);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/takeABook/{id}", method = RequestMethod.GET)
    public Boolean takeABook(@PathVariable("id") long userId, @RequestBody long bookId) {
        return bookService.takeABook(bookId, userId);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(value = "/passBook/{id}", method = RequestMethod.GET)
    public Boolean passBook(@PathVariable("id") long userId, @RequestBody long bookId) {
        return bookService.passBook(bookId, userId);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @RequestMapping(value = "/takeAReservation/{id}", method = RequestMethod.GET)
    public Boolean takeAReservation(@PathVariable("id") long bookId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userService.findOne(userDetails.getUsername());
        return bookService.takeAReservation(bookId, user.getId());
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @RequestMapping(value = "/passAReservation", method = RequestMethod.GET)
    public Boolean passAReservation(@RequestBody long bookId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User user = userService.findOne(userDetails.getUsername());
        return bookService.passAReservation(bookId, user.getId());
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Book>> listGenre() {
        List<Book> books = bookRepository.getFindAll();
        return ResponseEntity.ok().body(books);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(value = "/genre", method = RequestMethod.POST)
    public ResponseEntity<List<Book>> listGenre(@RequestBody String genre) {
        List<Book> books = bookRepository.getFindAllByGenre( genre);
        return ResponseEntity.ok().body(books);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(value = "/author", method = RequestMethod.POST)
    public ResponseEntity<List<Book>> listAuthor(@RequestBody String author) {
        List<Book> books = bookRepository.getFindAllByAuthor(author);
        return ResponseEntity.ok().body(books);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(value = "/author_and_genre", method = RequestMethod.POST)
    public ResponseEntity<List<Book>> listAuthorAndGenre(@RequestBody String author, @RequestBody String genre) {
        List<Book> books = bookRepository.getFindAllByAuthorGenre( author, genre);
        return ResponseEntity.ok().body(books);
    }

}
