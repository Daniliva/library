package com.example.demo.repo;


import com.example.demo.model.book.Book;
import com.example.demo.model.journals.JournalBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalBookRepository extends JpaRepository<JournalBook, Long> {

    @Query("SELECT j FROM JournalBook j  WHERE  j.book = ?1")
    JournalBook getByBookId(Book book);
}
