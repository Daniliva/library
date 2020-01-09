package com.example.demo.repository.book;

import com.example.demo.model.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE b.delete=false AND b.id=?1")
    Book findBookById(Long id);


    @Query("SELECT   b FROM Book b, JournalBook journalBook " +
            " WHERE b.delete=false " +
            "AND b.id=journalBook.book.id "
    )
    List<Book> getFindAll();

    @Query("SELECT b FROM Book  b, JournalBook journalBook " +
            " WHERE b.delete=false AND b.id=journalBook.book.id " +
            "AND b.genre=?1 ")
    List<Book> getFindAllByGenre(String genre);

    @Query("SELECT b FROM Book b " +
            " WHERE   b.author=?1 ")
    List<Book> getFindAllByAuthor(String author);

    @Query("SELECT b FROM Book b, JournalBook journalBook " +
            " WHERE  b.delete=false AND b.id=journalBook.book.id " +
            "AND  b.author=?1 and b.genre=?2  ")
    List<Book> getFindAllByAuthorGenre(String author, String genre);

    @Query(value = "SELECT b.id, author, b.delete, genre, name  FROM book b, journal_book journal_book " +
            " WHERE b.delete=false " +
            "AND b.id=journal_book.book_id " +
            "AND b.name=?1 " +
            "AND b.author=?2 " +
            "AND b.genre=?3 ",
            nativeQuery = true)
    List<Book> getFindAllByAuthorGenreAndName(String name, String author, String genre);

}
