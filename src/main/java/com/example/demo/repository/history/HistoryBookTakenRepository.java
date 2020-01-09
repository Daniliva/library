package com.example.demo.repository.history;

import com.example.demo.model.history.HistoryBookReservation;
import com.example.demo.model.history.HistoryBookTaken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HistoryBookTakenRepository extends JpaRepository<HistoryBookTaken, Long> {
    @Query("SELECT  BookT FROM HistoryBookTaken BookT")
    List<HistoryBookReservation> getAll();

    @Query("SELECT  BookT   " +
            "FROM HistoryBookReservation BookT  " +
            "WHERE BookT.book.id=?2   " +
            "AND BookT.user.id=?3   " +
            "AND BookT.dateReservation>?1"
    )
    List<HistoryBookTaken> getBookTaken(LocalDate date, Long bookId, Long userId);
    @Query("SELECT  BookT   " +
            "FROM HistoryBookTaken BookT  " +
            "WHERE BookT.user.id=?1  "
    )
    List<HistoryBookTaken> getUserTaken(Long userId);
    @Query("SELECT  BookT   " +
            "FROM HistoryBookTaken BookT  " +
            "WHERE BookT.book.id=?1  "
    )
    List<HistoryBookTaken> getBookTaken(Long bookId);
    /*SELECT book.name,book.author,book.genre, COUNT(book.name) AS total
    From book
    GROUP BY book.name,book.author,book.genre
    ORDER BY COUNT(book.name) DESC*/
    @Query(value="SELECT book.id,NAME,author " +
            "FROM history_book_taken,  book " +
            "WHERE book.id = history_book_taken.book_id " +
            "GROUP BY history_book_taken.book_id, book.id, name ,author " +
            "ORDER BY COUNT(history_book_taken.book_id) DESC",
            nativeQuery = true    )
    List<Object[]> getMostPopular();
    @Query(value="SELECT book.id,NAME,author " +
            "FROM history_book_taken, book " +
            "WHERE book.id = history_book_taken.book_id " +
            "GROUP BY history_book_taken.book_id, book.id, name ,author " +
            "ORDER BY COUNT(history_book_taken.book_id) ",
            nativeQuery = true
    )
    List<Object[]> getNotMostPopular();
   @Query(value="SELECT book.id,NAME,author " +
            "FROM history_book_taken, book " +
            "WHERE book.author=?1 " +
          "GROUP BY history_book_taken.book_id, book.id, name ,author " +
            "ORDER BY COUNT(history_book_taken.book_id) DESC",
             nativeQuery = true
    )
    List<Object[]> getMostPopularAuthor(String author);
     @Query(value="SELECT book.id,NAME,author " +
            "FROM history_book_taken, book " +
            "WHERE book.author=?1 " +
          "GROUP BY history_book_taken.book_id, book.id, name ,author " +
            "ORDER BY COUNT(history_book_taken.book_id) ",
             nativeQuery = true
    )
    List<Object[]> getNotMostPopularAuthor(String author);
      @Query(value="SELECT book.id,NAME,author " +
            "FROM history_book_taken, book " +
            "WHERE book.genre=?1 " +
          "GROUP BY history_book_taken.book_id, book.id, name ,author " +
            "ORDER BY COUNT(history_book_taken.book_id) DESC",
             nativeQuery = true
    )
    List<Object[]> getMostPopularGenre(String genre);
    @Query(value="SELECT book.id,NAME,author " +
            "FROM history_book_taken, book " +
            "WHERE book.genre=?1 " +
          "GROUP BY history_book_taken.book_id, book.id, name ,author " +
            "ORDER BY COUNT(history_book_taken.book_id) ",
             nativeQuery = true
    )
    List<Object[]> getNotMostPopularGenre(String genre);

}
