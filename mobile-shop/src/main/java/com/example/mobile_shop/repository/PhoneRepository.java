package com.example.mobile_shop.repository;

import com.example.mobile_shop.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
    // no extra methods needed for basic CRUD
}
