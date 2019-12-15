package com.example.demo.model.journals;

import com.example.demo.model.User;
import com.example.demo.model.book.Book;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class BookReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Book book;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private User user;
    @Column
    private LocalDate dateReservation;

    public BookReservation(Book book, User user, LocalDate dateReservation) {
        this.book = book;
        this.user = user;
        this.dateReservation = dateReservation;
    }

    public BookReservation() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDate dateReservation) {
        this.dateReservation = dateReservation;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
