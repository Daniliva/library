package com.example.demo.model.journals;

import com.example.demo.model.book.Book;

import javax.persistence.*;

@Entity
@Table
public class JournalBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Book book;
    @JoinColumn
    private boolean bookReservation;
    @JoinColumn
    private Long count;
    @JoinColumn
    private Long countTake;

    public JournalBook() {
        countTake = (long) 0;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }


    public boolean isBookReservation() {
        return bookReservation;
    }

    public void setBookReservation(boolean bookReservation) {
        this.bookReservation = bookReservation;
    }

    public Long getCountTake() {
        return countTake;
    }

    public void setCountTake(Long countTake) {
        this.countTake = countTake;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void plusOneToCountTake() {
        countTake++;
    }

    public void minusOneToCountTake() {
        countTake--;
    }
}
