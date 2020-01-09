package com.example.demo.model.history;

import com.example.demo.model.autorization.User;
import com.example.demo.model.book.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;

@Entity

public class HistoryBookReservation
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Book book;
    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private User user;
    @Column
    private LocalDate dateReservation;

    public HistoryBookReservation(Book book, User user, LocalDate dateReservation) {
        this.book = book;
        this.user = user;
        this.dateReservation = dateReservation;
    }

    public HistoryBookReservation() {
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
