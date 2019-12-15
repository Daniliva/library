package com.example.demo.repo;

import com.example.demo.model.journals.BookReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface ReservationRepository extends JpaRepository<BookReservation, Long> {
 /*  @Query("SELECT  COUNT(BookReservation) AS total FROM BookReservation WHERE BookReservation.book.id=?2  AND BookReservation.dateReservation>?1 GROUP BY book.id   ORDER BY total")
        Long getCountReservationWithBookId1(LocalDate date,Long id);*/
 @Query("SELECT  BookR FROM BookReservation BookR")
 List<BookReservation> getAll();
    @Query("SELECT  BookR   " +
            "FROM BookReservation BookR  " +
            "WHERE BookR.book.id=?2   " +
            "AND BookR.user.id=?3   " +
            "AND BookR.dateReservation>?1"
            )
       List<BookReservation> getBookReservationForDateBookAndUserId(LocalDate date, Long bookId, Long userId);

}
