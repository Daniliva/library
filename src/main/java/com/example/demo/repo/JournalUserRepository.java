package com.example.demo.repo;

import com.example.demo.model.User;
import com.example.demo.model.journals.JournalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalUserRepository extends JpaRepository<JournalUser, Long> {
    @Query("SELECT j FROM JournalUser j  WHERE  j.userId = ?1")
    JournalUser getByUserId(User user);
}
