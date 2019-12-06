package com.example.demo.service;

import com.example.demo.domain.Book;
import com.example.demo.dto.BookDTO;
import com.example.demo.repo.BookAndUserRepository;
import com.example.demo.repo.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;


@Service
public class BookService {

    private Logger log = LoggerFactory.getLogger(BookService.class);

    private BookRepository bookRepository;
    private BookAndUserRepository bookAndUserRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book save(BookDTO bookDTO) {
        Book user = new Book(bookDTO.getName(), bookDTO.getAuthor(), bookDTO.getGenre());
        return bookRepository.save(user);
    }

    public ResponseEntity<Book> getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (!book.isPresent())
            throw new EntityNotFoundException("id-" + id);
        return ResponseEntity.ok().body(book.get());
    }

    public ResponseEntity<Book> update(BookDTO book, Long bookId) throws EntityNotFoundException {
        Optional<Book> b = bookRepository.findById(bookId);
        if (!b.isPresent()) {
            throw new EntityNotFoundException("id-" + bookId);
        }
        b.get().setAuthor(book.getAuthor());
        b.get().setGenre(book.getGenre());
        b.get().setReading(book.isReading());
        b.get().setName(book.getName());
        return ResponseEntity.ok().body(bookRepository.save(b.get()));
    }


    public ResponseEntity<Book> takeABook(long bookId) {
        Optional<Book> b = bookRepository.findById(bookId);
        if (!b.isPresent()) {
            throw new EntityNotFoundException("id-" + bookId);
        }
        b.get().setReading(true);


        return ResponseEntity.ok().body(bookRepository.save(b.get()));
    }

    public ResponseEntity<Book> passBook(long bookId) {
        Optional<Book> b = bookRepository.findById(bookId);

        if (!b.isPresent()) {
            throw new EntityNotFoundException("id-" + bookId);
        }
        b.get().setReading(false);


        return ResponseEntity.ok().body(bookRepository.save(b.get()));

    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public ResponseEntity<Book> delete(Long personId)
            throws EntityNotFoundException {
        Optional<Book> p = bookRepository.findById(personId);
        if (!p.isPresent()) {
            throw new EntityNotFoundException("id-" + personId);
        }
        bookRepository.deleteById(personId);
        return ResponseEntity.ok().body(p.get());
    }
}
