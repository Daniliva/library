package com.example.demo.domain;

import com.example.demo.model.User;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "book_and_user")
public class BookAndUser {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    @Column
    private LocalDate dateReservation;
    @Column
    private boolean bookTaken;
    @Column
    private Long userid;
    @Column
    private Long book;

    public BookAndUser() {
    }





    public boolean isBookTaken() {
        return bookTaken;
    }

    public void setBookTaken(boolean bookTaken) {
        this.bookTaken = bookTaken;
    }

    public LocalDate getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDate dateReservation) {
        this.dateReservation = dateReservation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getBook() {
        return book;
    }

    public void setBook(Long book) {
        this.book = book;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid =  userid;
    }
}
