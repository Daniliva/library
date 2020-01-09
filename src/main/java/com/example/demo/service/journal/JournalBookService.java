package com.example.demo.service.journal;

import com.example.demo.model.book.Book;
import com.example.demo.model.history.HistoryBookTaken;
import com.example.demo.model.journals.JournalBook;
import com.example.demo.model.journals.JournalUser;
import com.example.demo.repository.history.HistoryBookTakenRepository;
import com.example.demo.repository.history.HistoryReservationRepository;
import com.example.demo.repository.journal.JournalBookRepository;
import com.example.demo.service.book.BookService;
import com.example.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Iterator;

@Service(value = "JournalBookService")
public class JournalBookService {
    @Autowired
    private JournalBookRepository journalBookRepository;
    @Autowired
    private JournalBookService journalBookService;
    @Autowired
    private HistoryReservationRepository historyReservationRepository;
    @Autowired
    private JournalUserService journalUserService;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    private HistoryBookTakenRepository historyBookTakenRepository;

    public void save(JournalBook journalBook) {
        journalBookRepository.save(journalBook);
    }

    public JournalBook getByBookId(Book book) {
        return journalBookRepository.getByBookId(book);
    }

    public boolean canReservation(JournalBook journalBook, Long userId) {
        Long bookId = journalBook.getBook().getId();
        return (getCountReservationWithBookId(LocalDate.now(), bookId) < journalBook.getCount() - journalBook.getCountTake() &&
                historyReservationRepository.getBookReservation(LocalDate.now(), bookId, userId).size() == 0);
    }

    public boolean canTakeABook(JournalBook journalBook, Long userId) {
        Long bookId = journalBook.getBook().getId();

       JournalUser journalUser= journalUserService.getByUserId( userService.findById(userId));

       Book book= journalBook.getBook();

        for (Iterator<Book> it =journalUser.getBooks().iterator(); it.hasNext(); ) {
            Book f = it.next();
            if (f.equals(book))
             {return false;}
        }
        return (getCountReservationWithBookId(LocalDate.now(), bookId) <= journalBook.getCount() - journalBook.getCountTake() &&
                historyReservationRepository.getBookReservation(LocalDate.now(), bookId, userId).size() <= 1);
    }

    public boolean canPassTakeABook(JournalBook journalBook, JournalUser journalUser) {
        for (Iterator<Book> it = journalUser.getBooks().iterator(); it.hasNext(); ) {
            Book f = it.next();
            if (f.equals(journalBook.getBook())) {
                return true;
            }
        }
        return false;
    }

    public boolean canPassAReservation(JournalBook journalBook, Long userId) {
        Long bookId = journalBook.getBook().getId();
        return historyReservationRepository.getBookReservation(LocalDate.now(), bookId, userId).size() > 0;
    }

    public long getCountReservationWithBookId(LocalDate date, Long bookId) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT  COUNT(BookR) AS total " +
                        "FROM HistoryBookReservation  BookR WHERE BookR.book.id=?2  " +
                        "AND BookR.dateReservation>?1 " +

                        "ORDER BY total",
                Long.class);
        query.setParameter(1, date);
        query.setParameter(2, bookId);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return 0;
        }
    }

    public void enterTakeABook(Book book, Long userId) {
        JournalBook journalBook = journalBookService.getByBookId(book);
        journalBook.plusOneToCountTake();
        journalBookService.save(journalBook);
        JournalUser journalUser = journalUserService.getByUserId(userService.findById(userId));
        journalUser.getBooks().add(book);
        journalUserService.save(journalUser);
        HistoryBookTaken historyBookTaken = new HistoryBookTaken();
        historyBookTaken.setBook(journalBook.getBook());
        historyBookTaken.setUser(journalUser.getUserId());
        historyBookTaken.setDateTaken(LocalDate.now());
        historyBookTakenRepository.save(historyBookTaken);
    }

    public Boolean createTakeABook(Book book, long userId) {
        JournalBook journalBook = journalBookService.getByBookId(book);
        JournalUser journalUser = journalUserService.getByUserId(userService.findById(userId));
        if (journalBookService.canPassTakeABook(journalBook, journalUser)) {
            journalBook.minusOneToCountTake();
            journalBookService.save(journalBook);
            journalUser.getBooks().remove(book);
            journalUserService.save(journalUser);
            return true; //ResponseEntity.ok().body(b.get());
        } else {
            throw new EntityNotFoundException("user id " + userId +
                    "now not read book  " + book.getId());
        }


    }
}
