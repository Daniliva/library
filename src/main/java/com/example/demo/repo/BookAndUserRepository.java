package com.example.demo.repo;

import com.example.demo.domain.BookAndUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookAndUserRepository extends JpaRepository<BookAndUser, Long> {
    BookAndUser findBookAndUserById(Long id);
    @Query("SELECT MAX(u.id) FROM BookAndUser u WHERE  u.book = ?1 and u.userid= ?2")
    BookAndUser findByBookIdAndUserId(Long bookId,Long userId);
}
