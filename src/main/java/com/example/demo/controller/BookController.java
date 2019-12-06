package com.example.demo.controller;

import com.example.demo.domain.Book;
import com.example.demo.dto.BookDTO;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("Book")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping
    public ResponseEntity<List<Book>> list() {
        List<Book> book = bookService.findAll();
        return ResponseEntity.ok().body(book);
    }

    @RequestMapping(value = "/getBook/{id}", method = RequestMethod.GET)
    public ResponseEntity<Book> getBook(@PathVariable("id") long bookId)
    {
        return bookService.getBookById(bookId);
    }


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Book> createBook(@RequestBody BookDTO book) {
        /*   message.setCreationDate(LocalDateTime.now());*/
        Book b = bookService.save(book);
        return ResponseEntity.status(201).body(b);
    }


    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Book>
    updateBook(@RequestBody @Valid BookDTO book,@PathVariable("id")Long bookId) {
        return bookService.update(book, bookId);
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Book> deleteBook(@PathVariable("id") Long personId) {
        return bookService.delete(personId);
    }


    @RequestMapping(value = "/takeABook/{id}", method = RequestMethod.GET)
    public ResponseEntity<Book> takeABook(@PathVariable("id") long bookId) {


        return bookService.takeABook(bookId);
    }

    @RequestMapping(value = "/passBook/{id}", method = RequestMethod.GET)
    public ResponseEntity<Book> passBook(@PathVariable("id") long bookId) {

        return bookService.passBook(bookId);
    }
}
