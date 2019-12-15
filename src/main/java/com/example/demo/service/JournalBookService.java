package com.example.demo.service;

import com.example.demo.model.book.Book;
import com.example.demo.model.journals.JournalBook;
import com.example.demo.repo.JournalBookRepository;
import com.example.demo.repo.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDate;

@Service(value = "JournalBookService")
public class JournalBookService {
    @Autowired
    private JournalBookRepository journalBookRepository;
    @Autowired
    private JournalBookService journalBookService;
    @Autowired
    private ReservationRepository reservationRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public void save(JournalBook journalBook) {
        journalBookRepository.save(journalBook);
    }

    public JournalBook getByBookId(Book book) {
        return journalBookRepository.getByBookId(book);
    }

    public boolean canReservation(JournalBook journalBook, Long userId) {
        Long bookId = journalBook.getBook().getId();
        return (getCountReservationWithBookId(LocalDate.now(), bookId) < journalBook.getCount() - journalBook.getCountTake() &&
                reservationRepository.getBookReservationForDateBookAndUserId(LocalDate.now(), bookId, userId).size() == 0);
    }

    //takeABook
    public boolean canTakeABook(JournalBook journalBook, Long userId) {
        Long bookId = journalBook.getBook().getId();
        return (getCountReservationWithBookId(LocalDate.now(), bookId) <= journalBook.getCount() - journalBook.getCountTake());
    }

    public boolean canPassAReservation(JournalBook journalBook, Long userId) {
        Long bookId = journalBook.getBook().getId();
        return reservationRepository.getBookReservationForDateBookAndUserId(LocalDate.now(), bookId, userId).size() >= 1;
    }

    private long getCountReservationWithBookId(LocalDate date, Long bookId) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT  COUNT(BookR) AS total " +
                        "FROM BookReservation  BookR WHERE BookR.book.id=?2  " +
                        "AND BookR.dateReservation>?1 " +
                        "GROUP BY BookR.book.id   ORDER BY total",
                Long.class);
        query.setParameter(1, date);
        query.setParameter(2, bookId);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return 0;
        }

    }
}
