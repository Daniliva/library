package com.example.demo.service.journal;

import com.example.demo.model.autorization.User;
import com.example.demo.model.book.Book;
import com.example.demo.model.history.HistoryBookReservation;
import com.example.demo.model.journals.JournalBook;
import com.example.demo.model.journals.JournalUser;
import com.example.demo.repository.history.HistoryReservationRepository;
import com.example.demo.repository.journal.JournalUserRepository;
import com.example.demo.service.book.BookReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;

@Service(value = "JournalUserService")
public class JournalUserService {
    @Autowired
    private JournalUserRepository journalUserRepository;
    @Autowired
    private JournalBookService journalBookService;
    @Autowired
    private JournalUserService journalUserService;
    @Autowired
    private BookReservationService bookReservationService;
    @Autowired
    private HistoryReservationRepository historyReservationRepository;

    public void save(JournalUser journalUser) {
        journalUserRepository.save(journalUser);
    }

    public JournalUser getByUserId(User user) {
        return journalUserRepository.getByUserId(user);
    }

    public Boolean createReservation(Book book,User user) {
        JournalBook journalBook = journalBookService.getByBookId(book);
        JournalUser journalUser = journalUserService.getByUserId(user);
        if (journalBookService.canReservation(journalBook, user.getId())) {
            LocalDate localDate = LocalDate.now().plusDays(7);
            bookReservationService.getReservation(user,book, localDate);
            journalUser.getBooksReservation().add(book);
            journalUserService.save(journalUser);
           /* HistoryBookReservation historyBookReservation = new HistoryBookReservation();
            historyBookReservation.setBook(book);
            historyBookReservation.setUser(user);
            historyBookReservation.setDateReservation(localDate);
            historyReservationRepository.save(historyBookReservation);*/
            return true;// ResponseEntity.ok().body(bookRepository.save(b.get()));
        } else {
            throw new EntityNotFoundException("book id-" + book.getId() + "now reservation or read");
        }
    }
}
