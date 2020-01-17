package com.example.demo.repository.user;


import com.example.demo.model.autorization.Role;
import com.example.demo.model.autorization.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    @Query("SELECT MAX(u.id) FROM User u ")
    Long lastId();
    @Query("SELECT u FROM User u WHERE u.roles=?1 ")
    List<User> findByRole(Set<Role> role);
}
