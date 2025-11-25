package com.example.user_login.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.user_login.Models.usermodel;

public interface UserRepository extends JpaRepository<usermodel, Long> {
    usermodel findByEmail(String email);
}