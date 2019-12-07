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
    Book findBookById(Long id);
    @Query("SELECT b FROM Book b  WHERE  b.journalBook.dateReservation < ?1 AND  b.journalBook.reading=false")
    List<Book> getFindAllByDate(LocalDate date);
}
