package com.example.demo.service;

import com.example.demo.domain.Book;
import com.example.demo.dto.BookDTO;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.repo.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookService {

    private Logger log = LoggerFactory.getLogger(BookService.class);

    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book save(BookDTO bookDTO) {
        Book user = new Book(bookDTO.getName(), bookDTO.getAuthor(), bookDTO.getGenre());
        return bookRepository.save(user);
    }

    public Book getBookById(Long id) {
        return bookRepository.findBookById(id);
    }

    public Book update(BookDTO bookDTO) throws Exception {
        if (bookRepository.findById(bookDTO.getId()).isPresent()) {
            Book book = bookRepository.findBookById(bookDTO.getId());
            book.setName(bookDTO.getName());
            book.setAuthor(bookDTO.getAuthor());
            book.setGenre(bookDTO.getGenre());
            book.setReading(bookDTO.isReading());
            return bookRepository.save(book);
        }
        log.warn("Can not update user - " + bookDTO.toString());
        throw new NotFoundException(
                "Can not update user - " + bookDTO.toString());
    }

    public Book takeABook(long bookId) {
        getBookById(bookId).setReading(true);
        return getBookById(bookId);
    }

    public Book passBook(long bookId) {
        getBookById(bookId).setReading(false);
        return getBookById(bookId);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public void delete(Book book) {
        bookRepository.delete(book);
    }
}
