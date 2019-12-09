package com.example.demo.model.book;

import com.example.demo.model.journals.JournalBook;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.time.LocalDate;

@Entity
@Table
@ToString(of = {"id", "name", "author", "genre", "reading"})
@EqualsAndHashCode(of = {"id"})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Null
    @JsonView(Views.Id.class)
    private Long id;
    @JsonView(Views.Name.class)
    private String name;
    @JsonView(Views.Author.class)
    private String author;
    @JsonView(Views.Genre.class)
    private String genre;
    @Null
    @JsonIgnore
    private boolean delete;


    public Book() {
    }

    public Book(String name, String author, String genre) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        delete=false;
    }


    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String autor) {
        this.author = autor;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }
}
