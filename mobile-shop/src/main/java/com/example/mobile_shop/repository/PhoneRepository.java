package com.example.mobile_shop.repository;

import com.example.mobile_shop.model.Phone;
import org.springframework.data.repository.CrudRepository;

public interface PhoneRepository extends CrudRepository<Phone, Long> {
    
}
