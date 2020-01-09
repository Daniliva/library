package com.example.demo.repository.user;

import com.example.demo.model.autorization.Role;
import com.example.demo.model.autorization.UserRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRegistrationRepository  extends JpaRepository<UserRegistration, Long> {
    @Query("SELECT j FROM UserRegistration j  WHERE  j.token= ?1")
    UserRegistration getByToken(String code);
    @Query("SELECT j FROM UserRegistration j  WHERE  j.user.username= ?1")
    UserRegistration getByUsername(String name);
}
