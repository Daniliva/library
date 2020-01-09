package com.example.demo.model.autorization;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class UserRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate dateAnswer;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private User user;
    private String token;
    private Boolean emailReal;

    public UserRegistration() {
        emailReal = false;
    }

    public UserRegistration(LocalDate dateAnswer, User user, String token) {
        this.dateAnswer = dateAnswer;
        this.user = user;
        this.token = token;
        emailReal = false;
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        token = token;
    }

    public LocalDate getDateAnswer() {
        return dateAnswer;
    }

    public void setDateAnswer(LocalDate dateAnswer) {
        this.dateAnswer = dateAnswer;
    }

    public Boolean getEmailReal() {
        return emailReal;
    }

    public void setEmailReal(Boolean emailReal) {
        this.emailReal = emailReal;
    }
}
