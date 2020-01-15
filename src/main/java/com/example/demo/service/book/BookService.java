package com.example.demo.service.book;

import com.example.demo.dto.book.BookAnswerDTO;
import com.example.demo.dto.book.BookDTO;
import com.example.demo.model.autorization.User;
import com.example.demo.model.book.Book;
import com.example.demo.model.journals.JournalBook;
import com.example.demo.repository.book.BookRepository;
import com.example.demo.repository.history.HistoryReservationRepository;
import com.example.demo.service.journal.JournalBookService;
import com.example.demo.service.journal.JournalUserService;
import com.example.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BookReservationService bookReservationService;
    @Autowired
    private JournalBookService journalBookService;
    @Autowired
    private JournalUserService journalUserService;
    @Autowired
    private HistoryReservationRepository historyReservationRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book save(BookDTO bookDTO) {
        Book book = new Book(bookDTO.getName(), bookDTO.getAuthor(), bookDTO.getGenre());
        bookRepository.save(book);
        return book;
    }

    public ResponseEntity<BookAnswerDTO> getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (!book.isPresent()) {
            throw new EntityNotFoundException("id-" + id);
        }
        JournalBook journalBook = journalBookService.getByBookId(book.get());
        Long countReservation = journalBookService.getCountReservation(LocalDate.now(), book.get().getId());
        BookAnswerDTO bookAnswerDTO = BookAnswerDTOService.createBookAnswerDTO(journalBook, book.get(), countReservation);
        return ResponseEntity.ok().body(bookAnswerDTO);
    }

    public ResponseEntity<BookAnswerDTO> update(BookDTO book, Long bookId) throws EntityNotFoundException {
        Optional<Book> b = bookRepository.findById(bookId);
        if (!b.isPresent()) {
            throw new EntityNotFoundException("id-" + bookId);
        }
        b.get().setAuthor(book.getAuthor());
        b.get().setGenre(book.getGenre());
        b.get().setName(book.getName());
        JournalBook journalBook = journalBookService.getByBookId(b.get());
        long countNotFree=journalBookService.getCountReservation(LocalDate.now(), b.get().getId())
                + journalBook.getCount();
        if ( countNotFree<= book.getCount()) {
            journalBook.setCount(book.getCount());
            journalBookService.save(journalBook);
        }
        bookRepository.save(b.get());
        return getBookById(b.get().getId());
    }

    public Boolean takeABook(long bookId, long userId) {
        Optional<Book> b = bookRepository.findById(bookId);
        if (!b.isPresent()) {
            throw new EntityNotFoundException("id-" + bookId);
        }
        JournalBook journalBook = journalBookService.getByBookId(b.get());
        if (journalBookService.canTakeABook(journalBook, userId)) {
            if (historyReservationRepository.getBookReservation(LocalDate.now(), bookId, userId).size() > 0) {
                passAReservation(bookId, userId);
            }
            journalBookService.enterTakeABook(b.get(), userId);
            return true;
        } else {
            throw new EntityNotFoundException("book id-" + bookId + "now not free or you read this book");
        }
    }

    public Boolean passBook(long bookId, long userId) {
        Optional<Book> b = bookRepository.findById(bookId);
        if (!b.isPresent()) {
            throw new EntityNotFoundException("id-" + bookId);
        } else {
            return journalBookService.createTakeABook(b.get(), userId);
        }
    }

    public Boolean takeAReservation(long bookId, long userId) {
        Optional<Book> b = bookRepository.findById(bookId);
        User user = userService.findById(userId);
        if (!b.isPresent()) {
            throw new EntityNotFoundException("book id-" + bookId);
        } else if (user == null) {
            throw new EntityNotFoundException("user id-" + userId);
        } else {
            return journalUserService.createReservation(b.get(), user);
        }
    }

    public Boolean passAReservation(long bookId, long userId) {
        Optional<Book> b = bookRepository.findById(bookId);
        User user = userService.findById(userId);
        if (!b.isPresent()) {
            throw new EntityNotFoundException("book id-" + bookId);
        } else if (user == null) {
            throw new EntityNotFoundException("user id-" + userId);
        } else {
            JournalBook journalBook = journalBookService.getByBookId(b.get());
            if (journalBookService.canPassAReservation(journalBook, userId)) {
                bookReservationService.passReservation(bookId, userId);
                return true;
            } else {
                return false;
            }
        }//ResponseEntity.ok().body(bookRepository.save(b.get()));
    }

    public List<Book> findAll() {
        return bookRepository.getFindAll();
    }

    public ResponseEntity<Book> delete(Long personId)
            throws EntityNotFoundException {
        Optional<Book> p = bookRepository.findById(personId);
        if (!p.isPresent()) {
            throw new EntityNotFoundException("id-" + personId);
        }
        bookRepository.findBookById(personId).setDelete(true);
        return ResponseEntity.ok().body(p.get());
    }
}
