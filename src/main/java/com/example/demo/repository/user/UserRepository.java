package com.example.demo.repository.user;


import com.example.demo.model.autorization.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    @Query("SELECT MAX(u.id) FROM User u ")
    Long lastId();
}
