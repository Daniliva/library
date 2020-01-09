package com.example.demo.repository.history;

import com.example.demo.model.history.HistoryBookReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HistoryReservationRepository extends JpaRepository<HistoryBookReservation, Long> {

    @Query("SELECT  BookR FROM HistoryBookReservation BookR")
    List<HistoryBookReservation> getAll();

    @Query("SELECT  BookR   " +
            "FROM HistoryBookReservation BookR  " +
            "WHERE BookR.book.id=?2   " +
            "AND BookR.user.id=?3   " +
            "AND BookR.dateReservation>?1"
    )
    List<HistoryBookReservation> getBookReservation(LocalDate date, Long bookId, Long userId);

    @Query("SELECT  BookR   " +
            "FROM HistoryBookReservation BookR  " +
            "WHERE BookR.user.id=?1  "
    )
    List<HistoryBookReservation> getUserReservation(Long userId);

    @Query("SELECT  BookR   " +
            "FROM HistoryBookReservation BookR  " +
            "WHERE BookR.book.id=?1  "
    )
    List<HistoryBookReservation> getBookReservation(Long bookId);

    @Query(value = "SELECT book.id,NAME,author " +
            "FROM history_book_reservation,  book " +
            "WHERE book.id = history_book_reservation.book_id " +
            "GROUP BY history_book_reservation.book_id, book.id, name ,author " +
            "ORDER BY COUNT(history_book_reservation.book_id) DESC",
            nativeQuery = true)
    List<Object[]> getMostPopular();

    @Query(value = "SELECT book.id,NAME,author " +
            "FROM history_book_reservation, book " +
            "WHERE book.id = history_book_reservation.book_id " +
            "GROUP BY history_book_reservation.book_id, book.id, name ,author " +
            "ORDER BY COUNT(history_book_reservation.book_id) ",
            nativeQuery = true
    )
    List<Object[]> getNotMostPopular();

    @Query(value = "SELECT book.id,NAME,author " +
            "FROM history_book_reservation, book " +
            "WHERE book.author=?1 " +
            "GROUP BY history_book_reservation.book_id, book.id, name ,author " +
            "ORDER BY COUNT(history_book_reservation.book_id) DESC",
            nativeQuery = true
    )
    List<Object[]> getMostPopularAuthor(String author);

    @Query(value = "SELECT book.id,NAME,author " +
            "FROM history_book_reservation, book " +
            "WHERE book.author=?1 " +
            "GROUP BY history_book_reservation.book_id, book.id, name ,author " +
            "ORDER BY COUNT(history_book_reservation.book_id) ",
            nativeQuery = true
    )
    List<Object[]> getNotMostPopularAuthor(String author);

    @Query(value = "SELECT book.id,NAME,author " +
            "FROM history_book_reservation, book " +
            "WHERE book.genre=?1 " +
            "GROUP BY history_book_reservation.book_id, book.id, name ,author " +
            "ORDER BY COUNT(history_book_reservation.book_id) DESC",
            nativeQuery = true
    )
    List<Object[]> getMostPopularGenre(String genre);

    @Query(value = "SELECT book.id,NAME,author " +
            "FROM history_book_reservation, book " +
            "WHERE book.genre=?1 " +
            "GROUP BY history_book_reservation.book_id, book.id, name ,author " +
            "ORDER BY COUNT(history_book_reservation.book_id) ",
            nativeQuery = true
    )
    List<Object[]> getNotMostPopularGenre(String genre);


}
