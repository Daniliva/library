package com.example.demo.repo;

import com.example.demo.model.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE b.delete=false and b.id=?1")
    Book findBookById(Long id);

    @Query("SELECT b FROM Book b, JournalBook journalBook " +
            " WHERE b.delete=false and b.id=journalBook.book.id " +
            "AND b.genre=?2 AND" +
            " journalBook.dateReservation < ?1 AND  journalBook.reading=false")
    List<Book> getFindAllByDateAndGenre(LocalDate date, String genre);

    @Query("SELECT b FROM Book b, JournalBook journalBook " +
            " WHERE b.delete=false and b.id=journalBook.book.id " +
            "and b.author=?2 AND" +
            " journalBook.dateReservation < ?1 AND  journalBook.reading=false")
    List<Book> getFindAllByDateAndAuthor(LocalDate date, String author);

    @Query("SELECT b FROM Book b, JournalBook journalBook " +
            " WHERE  b.delete=false and b.id=journalBook.book.id " +
            "and b.author=?2 and b.genre=?3 AND" +
            " journalBook.dateReservation < ?1 AND  journalBook.reading=false")
    List<Book> getFindAllByDateAndAuthorGenre(LocalDate date, String author, String genre);

    @Query("SELECT   b FROM Book b, JournalBook journalBook " +
            " WHERE b.delete=false " +
            "AND b.id=journalBook.book.id AND " +
            "journalBook.dateReservation < ?1 AND journalBook.reading=false GROUP BY b.id"
    )
    List<Book> getFindAllByDate(LocalDate date);

    @Query("SELECT b FROM  Book b, JournalBook journalBook " +
            " WHERE b.delete=false" +
            " AND b.id=journalBook.book.id " +
            "AND b.genre=?2 AND" +
            " journalBook.dateReservation < ?1 AND journalBook.reading=false")
    Book findFindAllByDateAndGenre(LocalDate date, String genre);

    @Query("SELECT b FROM Book b, JournalBook journalBook " +
            " WHERE b.id=journalBook.book.id " +
            "AND b.author=?2 AND" +
            " journalBook.dateReservation < ?1 AND  journalBook.reading=false")
    Book findFindAllByDateAndAuthor(LocalDate date, String author);

    @Query("SELECT b FROM Book b, JournalBook journalBook " +
            " WHERE b.id=journalBook.book.id " +
            "AND b.author=?2 AND b.genre=?3 AND" +
            " journalBook.dateReservation < ?1 AND  journalBook.reading=false")
    Book findFindAllByDateAndAuthorGenre(LocalDate date, String author, String genre);

    @Query("SELECT DISTINCT b.name,  b.author, b.genre  FROM Book b, JournalBook journalBook " +
            " WHERE b.delete=false " +
            "AND b.id=journalBook.book.id AND " +
            "journalBook.dateReservation < ?1 AND journalBook.reading=false "
    )
    List<String> getFindAll(LocalDate date);

    @Query(value = "SELECT b.id, author, b.delete, genre, name  FROM book b, journal_book journal_book " +
            " WHERE b.delete=false " +
            "AND b.id=journal_book.book_id " +
            "AND journal_book.date_reservation < ?1 " +
            "AND journal_book.reading=false " +
            "AND b.name=?2 " +
            "AND b.author=?3 " +
            "AND b.genre=?4 ",
            nativeQuery = true)
    List<Book> getFindAllByDateAuthorGenreAndName(LocalDate date,String name, String author, String genre);

}
