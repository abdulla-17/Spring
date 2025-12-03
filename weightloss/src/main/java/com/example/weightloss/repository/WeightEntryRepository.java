package com.example.weightloss.repository;

import com.example.weightloss.model.User;
import com.example.weightloss.model.WeightEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WeightEntryRepository extends JpaRepository<WeightEntry, Long> {
    boolean existsByUserAndEntryDate(User user, LocalDate entryDate);
    Optional<WeightEntry> findByUserAndEntryDate(User user, LocalDate entryDate);
    Page<WeightEntry> findByUserOrderByEntryDateDesc(User user, Pageable pageable);
    List<WeightEntry> findByUserAndEntryDateBetweenOrderByEntryDate(User user, LocalDate from, LocalDate to);
}
