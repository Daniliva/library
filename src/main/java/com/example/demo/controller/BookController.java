package com.example.demo.controller;

import com.example.demo.domain.Book;
import com.example.demo.dto.BookDTO;
import com.example.demo.service.BookService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Book")
public class BookController {
    // private final BookRepository bookRepository;
    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {

        this.bookService = bookService;
    }


    @GetMapping
    public List<Book> list() {
        return bookService.findAll();
    }

    @RequestMapping(value = "/getOne/{id}", method = RequestMethod.GET)
    public Book getOne(@PathVariable("id") long bookId) {
        return bookService.getBookById(bookId);
    }

    //@PostMapping
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Book create(@RequestBody BookDTO book) {
        /*   message.setCreationDate(LocalDateTime.now());*/
        return bookService.save(book);
    }

    //@PutMapping("{id}")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public Book update(
            @PathVariable("id") BookDTO bookFromDb,
            @RequestBody BookDTO book
    ) {
        BeanUtils.copyProperties(book, bookFromDb, "id");

        return bookService.save(bookFromDb);
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Book book) {
        bookService.delete(book);
    }


    @RequestMapping(value = "/takeABook/{id}", method = RequestMethod.GET)
    public Book takeABook(@PathVariable("id") long bookId) {
        return bookService.takeABook(bookId);
    }

    @RequestMapping(value = "/passBook/{id}", method = RequestMethod.GET)
    public Book passBook(@PathVariable("id") long bookId) {
        return bookService.passBook(bookId);
    }
}
