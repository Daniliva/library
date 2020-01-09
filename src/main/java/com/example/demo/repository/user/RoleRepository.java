package com.example.demo.repository.user;


import com.example.demo.model.autorization.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT j FROM Role j  WHERE  j.name = ?1")
    Role getByRoleName(String role);
}
