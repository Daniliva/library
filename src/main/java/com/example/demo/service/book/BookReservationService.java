package com.example.demo.service.book;

import com.example.demo.model.autorization.User;
import com.example.demo.model.book.Book;
import com.example.demo.model.history.HistoryBookReservation;
import com.example.demo.repository.history.HistoryReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookReservationService {
    @Autowired
    private HistoryReservationRepository historyReservationRepository;

    public void getReservation(User user, Book b, LocalDate localDate) {
        HistoryBookReservation historyBookReservation = new HistoryBookReservation();
        historyBookReservation.setBook(b);
        historyBookReservation.setUser(user);
        historyBookReservation.setDateReservation(localDate);
        historyReservationRepository.save(historyBookReservation);
    }

    public void passReservation(Long bookId, Long userId) {
        List<HistoryBookReservation> historyBookReservation =
                historyReservationRepository.getBookReservation(LocalDate.now(), bookId, userId);
        for (int i = 0; i < historyBookReservation.size(); i++) {
            historyBookReservation.get(i).setDateReservation(LocalDate.now().minusDays((long) 1));
        }
        historyReservationRepository.saveAll(historyBookReservation);
    }
}
