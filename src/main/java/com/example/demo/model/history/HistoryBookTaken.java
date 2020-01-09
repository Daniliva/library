package com.example.demo.model.history;

import com.example.demo.model.autorization.User;
import com.example.demo.model.book.Book;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
public class HistoryBookTaken {
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
    private LocalDate dateTaken;

    public HistoryBookTaken() {
    }

    public HistoryBookTaken(Book book, User user, LocalDate dateTaken) {
        this.book = book;
        this.user = user;
        this.dateTaken = dateTaken;
    }

    public LocalDate getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(LocalDate dateTaken) {
        this.dateTaken = dateTaken;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
