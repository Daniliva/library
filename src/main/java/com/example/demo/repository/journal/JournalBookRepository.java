package com.example.demo.repository.journal;


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
/*
    @Query("SELECT j FROM JournalBook j  ORDER BY j.countTake")
    List<JournalBook> getByMostPopularBook();

    @Query("SELECT j FROM JournalBook j  ORDER BY j.countTake DESC ")
    List<JournalBook> getByNotPopularBook();

    @Query("SELECT j FROM JournalBook j where j.book.author=?1 ORDER BY j.countTake  ")
    List<JournalBook> getByMostPopularBookOfAuthor(String author);

    @Query("SELECT j FROM JournalBook j where j.book.author=?1 ORDER BY j.countTake  DESC")
    List<JournalBook> getByNotPopularBookOfAuthor(String author);

    @Query("SELECT j FROM JournalBook j where j.book.genre=?1 ORDER BY j.countTake  ")
    List<JournalBook> getByMostPopularBookOfGenre(String genre);

    @Query("SELECT j FROM JournalBook j where j.book.genre=?1 ORDER BY j.countTake  DESC")
    List<JournalBook> getByNotPopularBookOfGenre(String genre);*/
}
