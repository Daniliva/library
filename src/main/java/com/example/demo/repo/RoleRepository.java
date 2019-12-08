package com.example.demo.repo;


import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.journals.JournalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT j FROM Role j  WHERE  j.name = ?1")
    Role getByRoleName(String role);
}
