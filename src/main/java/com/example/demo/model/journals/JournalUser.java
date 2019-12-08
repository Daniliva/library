package com.example.demo.model.journals;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.book.Book;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table
public class JournalUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private User userId;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_BOOK",
            joinColumns = {
                    @JoinColumn(name = "USER_ID", referencedColumnName="id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "BOOK_ID", referencedColumnName="id")})
    private Set<Book> books;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_BOOK_Reservation",
            joinColumns = {
                    @JoinColumn(name = "USER_ID", referencedColumnName="id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "BOOK_ID", referencedColumnName="id")})
    private Set<Book> booksReservation;
    public JournalUser() {
        booksReservation  = new HashSet<Book>();
        books = new HashSet<Book>();
    }

    public long getId() {
        return id;
    }



    public void setId(long id) {
        this.id = id;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Set<Book> getBooksReservation() {
        return booksReservation;
    }

    public void setBooksReservation(Set<Book> booksReservation) {
        this.booksReservation = booksReservation;
    }
}
