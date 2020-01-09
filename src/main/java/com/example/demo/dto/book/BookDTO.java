package com.example.demo.dto.book;

public class BookDTO {

    private Long     id;
    private String   name;
    private String   author;
    private String   genre;
   // private boolean  reading;
    private Long     count;
    public BookDTO(String name, String author, String genre) {
        this.name =   name;
        this.author = author;
        this.genre =  genre;
       // reading =     false;
    }

    public BookDTO() {

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

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "id = " + id +
                ", name = '" + name + '\'' +
                ", author = '" + author + '\'' +
                ", genre = '" + genre + '\'' +
              //  ", surname = '" + reading + '\'' +
                '}';
    }
}
