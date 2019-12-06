package com.example.demo.dto;

public class BookDTO {

    private Long     id;
    private String   name;
    private String   author;
    private String   genre;
    private boolean  reading;

    public BookDTO(String name, String author, String genre) {
        this.name =   name;
        this.author = author;
        this.genre =  genre;
        reading =     false;
    }

    public BookDTO() {

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


    @Override
    public String toString() {
        return "UserDTO{" +
                "id = " + id +
                ", name = '" + name + '\'' +
                ", surname = '" + author + '\'' +
                ", surname = '" + genre + '\'' +
                ", surname = '" + reading + '\'' +
                '}';
    }
}
