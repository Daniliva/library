package com.example.demo.repo;


import com.example.demo.model.book.Book;
import com.example.demo.model.journals.JournalBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JournalBookRepository extends JpaRepository<JournalBook, Long> {

    @Query("SELECT j FROM JournalBook j  WHERE  j.book = ?1")
    JournalBook getByBookId(Book book);

    @Query("SELECT j.book FROM JournalBook j  ORDER BY j.countTake")
    List<Book> getByMostPopularBook();

    @Query("SELECT j.book FROM JournalBook j  ORDER BY j.countTake DESC ")
    List<Book> getByNotPopularBook();

    @Query("SELECT j.book FROM JournalBook j where j.book.author=?1 ORDER BY j.countTake  ")
    List<Book> getByMostPopularBookOfAuthor(String author);

    @Query("SELECT j.book FROM JournalBook j where j.book.author=?1 ORDER BY j.countTake  DESC")
    List<Book> getByNotPopularBookOfAuthor(String author);

    @Query("SELECT j.book FROM JournalBook j where j.book.genre=?1 ORDER BY j.countTake  ")
    List<Book> getByMostPopularBookOfGenre(String genre);

    @Query("SELECT j.book FROM JournalBook j where j.book.genre=?1 ORDER BY j.countTake  DESC")
    List<Book> getByNotPopularBookOfGenre(String genre);
}
