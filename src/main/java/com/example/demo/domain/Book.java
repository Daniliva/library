package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table
@ToString(of = {"id", "name", "author", "genre", "reading"})
@EqualsAndHashCode(of = {"id"})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.Id.class)
    private Long id;
    @JsonView(Views.Name.class)
    private String name;
    @JsonView(Views.Author.class)
    private String author;
    @JsonView(Views.Genre.class)
    private String genre;
    @JsonView(Views.Reading.class)
    private boolean reading;


    public Book() {
    }

    public Book(String name, String author, String genre) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        reading = false;
    }

    public boolean isReading() {
        return reading;
    }

    public void setReading(boolean reading) {
        this.reading = reading;
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

}
