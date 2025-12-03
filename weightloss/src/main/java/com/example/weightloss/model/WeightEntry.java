package com.example.weightloss.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "weight_entries",
       uniqueConstraints = @UniqueConstraint(name = "uk_user_date", columnNames = {"user_id", "entry_date"}))
public class WeightEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "entry_date", nullable = false)
    private LocalDate entryDate;

    @Column(name = "weight_kg", nullable = false)
    private Double weightKg;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    public WeightEntry() {}

    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
    }

    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public LocalDate getEntryDate() { return entryDate; }
    public void setEntryDate(LocalDate entryDate) { this.entryDate = entryDate; }
    public Double getWeightKg() { return weightKg; }
    public void setWeightKg(Double weightKg) { this.weightKg = weightKg; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
