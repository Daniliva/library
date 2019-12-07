package com.example.demo.service;

import com.example.demo.dto.BookDTO;
import com.example.demo.model.book.Book;
import com.example.demo.model.journals.JournalBook;
import com.example.demo.model.journals.JournalUser;
import com.example.demo.repo.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class BookService {

    private Logger log = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserService userService;
    private JournalBookService journalBookService;
    private JournalUserService journalUserService;

    public BookService(BookRepository bookRepository) {
        journalBookService = new JournalBookService();
        journalUserService = new JournalUserService();
        this.bookRepository = bookRepository;
    }


    public Book save(BookDTO bookDTO) {
        Book book = new Book(bookDTO.getName(), bookDTO.getAuthor(), bookDTO.getGenre());
        bookRepository.save(book);
        journalBookService.save(book);
        book.journalBook = journalBookService.getByBookId(book);
        return book;
    }

    public ResponseEntity<Book> getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (!book.isPresent()) {
            throw new EntityNotFoundException("id-" + id);
        }
        return ResponseEntity.ok().body(book.get());
    }

    public ResponseEntity<Book> update(BookDTO book, Long bookId) throws EntityNotFoundException {
        Optional<Book> b = bookRepository.findById(bookId);
        if (!b.isPresent()) {
            throw new EntityNotFoundException("id-" + bookId);
        }
        b.get().setAuthor(book.getAuthor());
        b.get().setGenre(book.getGenre());
        b.get().setName(book.getName());
        return ResponseEntity.ok().body(bookRepository.save(b.get()));
    }


    public ResponseEntity<Book> takeABook(long bookId, long userId) {
        Optional<Book> b = bookRepository.findById(bookId);
        if (!b.isPresent()) {
            throw new EntityNotFoundException("id-" + bookId);
        }
        JournalBook journalBook = journalBookService.getByBookId(b.get());
        journalBook.setReading(true);
        journalBook.plusOneToCountTake();
        journalBookService.save(journalBook);
        JournalUser journalUser = journalUserService.getByUserId(userService.findById(userId));
        journalUser.getBooks().add(b.get());
        return ResponseEntity.ok().body(bookRepository.save(b.get()));
    }

    public ResponseEntity<Book> passBook(long bookId, long userId) {
        Optional<Book> b = bookRepository.findById(bookId);
        if (!b.isPresent()) {
            throw new EntityNotFoundException("id-" + bookId);
        }
        JournalBook journalBook = journalBookService.getByBookId(b.get());
        journalBook.setReading(false);
        journalBookService.save(journalBook);
        return ResponseEntity.ok().body(b.get());
    }

    public ResponseEntity<Book> takeAReservation(long bookId, long userId) {
        Optional<Book> b = bookRepository.findById(bookId);
        if (!b.isPresent()) {
            throw new EntityNotFoundException("id-" + bookId);
        }
        JournalBook journalBook = journalBookService.getByBookId(b.get());
        journalBook.plusOneToCountTake();
        LocalDate localDate = LocalDate.now();
        localDate.plusDays(7);
        journalBook.setDateReservation(localDate);
        journalBookService.save(journalBook);
        JournalUser journalUser = journalUserService.getByUserId(userService.findById(userId));
        journalUser.getBooksReservation().add(b.get());
        return ResponseEntity.ok().body(bookRepository.save(b.get()));
    }
    public ResponseEntity<Book>passAReservation(long bookId, long userId) {
        Optional<Book> b = bookRepository.findById(bookId);
        if (!b.isPresent()) {
            throw new EntityNotFoundException("id-" + bookId);
        }
        JournalBook journalBook = journalBookService.getByBookId(b.get());
        journalBook.getDateReservation().minusDays(7);
        journalBookService.save(journalBook);
        return ResponseEntity.ok().body(bookRepository.save(b.get()));
    }

    public List<Book> findAll() {
        return bookRepository.getFindAllByDate(LocalDate.now());
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
