package com.example.demo.repo;

import com.example.demo.model.book.Book;
import com.example.demo.model.journals.JournalBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE b.id=?1")
    Book findBookById(Long id);
    @Query("SELECT b FROM Book b, JournalBook journalBook " +
            " WHERE b.id=journalBook.book.id " +
            "and b.genre=?2 and" +
            " journalBook.dateReservation < ?1 AND  journalBook.reading=false" )
    List<Book> getFindAllByDateAndGenre(LocalDate date,String Genre);
    @Query("SELECT b FROM Book b, JournalBook journalBook " +
            " WHERE b.id=journalBook.book.id " +
            "and b.author=?2 and" +
            " journalBook.dateReservation < ?1 AND  journalBook.reading=false" )
    List<Book> getFindAllByDateAndAuthor(LocalDate date,String Author);
    @Query("SELECT b FROM Book b, JournalBook journalBook " +
            " WHERE b.id=journalBook.book.id " +
            "and b.author=?2 and b.genre=?3 and" +
            " journalBook.dateReservation < ?1 AND  journalBook.reading=false" )
    List<Book> getFindAllByDateAndAuthorGenre(LocalDate date,String Author,String Genre);
    @Query("SELECT b FROM Book b, JournalBook journalBook " +
            " WHERE b.id=journalBook.book.id and" +
            " journalBook.dateReservation < ?1 AND  " +
            "journalBook.reading=false")
    List<Book> getFindAllByDate(LocalDate date);//limit 1




    @Query("SELECT b FROM  Book b, JournalBook journalBook " +
            " WHERE b.id=journalBook.book.id " +
            "and b.genre=?2 and" +
            " journalBook.dateReservation < ?1 AND  journalBook.reading=false" )
   Book findFindAllByDateAndGenre(LocalDate date,String Genre);
    @Query("SELECT b FROM Book b, JournalBook journalBook " +
            " WHERE b.id=journalBook.book.id " +
            "and b.author=?2 and" +
            " journalBook.dateReservation < ?1 AND  journalBook.reading=false" )
   Book findFindAllByDateAndAuthor(LocalDate date,String Author);
    @Query("SELECT b FROM Book b, JournalBook journalBook " +
            " WHERE b.id=journalBook.book.id " +
            "and b.author=?2 and b.genre=?3 and" +
            " journalBook.dateReservation < ?1 AND  journalBook.reading=false" )
    Book findFindAllByDateAndAuthorGenre(LocalDate date,String Author,String Genre);
}
