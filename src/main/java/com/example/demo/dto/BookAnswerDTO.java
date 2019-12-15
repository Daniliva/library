package com.example.demo.dto;

public class BookAnswerDTO {
    private Long     id;
    private String   name;
    private String   author;
    private String   genre;
    private Long     count;
    private Long     countTake;
    private Long     countReservation;
    private boolean  reading;

    public BookAnswerDTO(String name, String author, String genre) {
        this.name =   name;
        this.author = author;
        this.genre =  genre;
        reading =     false;
    }

    public BookAnswerDTO() {

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

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getCountReservation() {
        return countReservation;
    }

    public void setCountReservation(Long countReservation) {
        this.countReservation = countReservation;
    }

    public Long getCountTake() {
        return countTake;
    }

    public void setCountTake(Long countTake) {
        this.countTake = countTake;
    }

    @Override
    public String toString() {
        return "BookAnswerDTO{" +
                ", id = '" + id + '\'' +
                ", name = '" + name + '\'' +
                ", surname = '" + author + '\'' +
                ", surname = '" + genre + '\'' +
                ", surname = '" + reading + '\'' +
                ", count = '" + count + '\'' +
                ", countTake = '" + countTake + '\'' +
                ", countReservation = '" + countReservation + '\'' +
                '}';
    }
}
