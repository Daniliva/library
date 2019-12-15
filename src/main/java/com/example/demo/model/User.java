package com.example.demo.model;

import com.example.demo.model.journals.JournalBook;
import com.example.demo.model.journals.JournalUser;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "user1")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;
    @Column
    private String username;
    @Column
    @JsonIgnore
    private String password;
    @Column
    private long salary;
    @Column
    private int age;
    @Column
    private LocalDate dateRegistration;
    @Column
    private long countBook;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLES",
            joinColumns = {
                    @JoinColumn(name = "USER_ID")},
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLE_ID")})
    private Set<Role> roles;
  /*  @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL  , fetch = FetchType.LAZY)
    private JournalUser journalUser;*/
    public User() {
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


/*
  public JournalUser getJournalUser() {
        return journalUser;
    }

    public void setJournalUser(JournalUser journalUser) {
        this.journalUser = journalUser;
    }*/

    public long getCountBook() {
        return countBook;
    }

    public void setCountBook(long countBook) {
        this.countBook = countBook;
    }

    public LocalDate getDateRegistration() {
        return dateRegistration;
    }

    public void setDateRegistration(LocalDate dateRegistration) {
        this.dateRegistration = dateRegistration;
    }
}
