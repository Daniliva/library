package com.example.demo.model.journals;

import com.example.demo.model.book.Book;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class JournalBook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Book book;
    @JoinColumn
    private boolean reading;
    @JoinColumn
    private boolean bookReservation;
    @JoinColumn
    private LocalDate dateReservation;
    @JoinColumn
    private Long countTake;

    public JournalBook() {
        countTake = (long) 0;
    }

    public LocalDate getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDate dateReservation) {
        this.dateReservation = dateReservation;
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

    public boolean isReading() {
        return reading;
    }

    public void setReading(boolean reading) {
        this.reading = reading;
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
}
