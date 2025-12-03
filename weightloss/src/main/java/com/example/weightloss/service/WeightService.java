package com.example.weightloss.service;

import com.example.weightloss.model.User;
import com.example.weightloss.model.WeightEntry;
import com.example.weightloss.repository.WeightEntryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
public class WeightService {
    private final WeightEntryRepository repo;
    private final ZoneId zone = ZoneId.systemDefault(); // change to Asia/Kolkata if you want

    public WeightService(WeightEntryRepository repo) {
        this.repo = repo;
    }

    public WeightEntry addWeight(User user, Double weightKg) {
        if (weightKg == null || weightKg <= 0) throw new IllegalArgumentException("Invalid weight");
        LocalDate today = LocalDate.now(zone);
        if (repo.existsByUserAndEntryDate(user, today)) {
            throw new IllegalStateException("Weight for today already added");
        }
        WeightEntry e = new WeightEntry();
        e.setUser(user);
        e.setEntryDate(today);
        e.setWeightKg(weightKg);
        return repo.save(e);
    }

    public Page<WeightEntry> list(User user, int page, int size) {
        Pageable p = PageRequest.of(page, size);
        return repo.findByUserOrderByEntryDateDesc(user, p);
    }

    public WeightEntry edit(User user, Long id, Double newWeight) {
        WeightEntry e = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Entry not found"));
        if (!e.getUser().getId().equals(user.getId())) throw new SecurityException("Not owner");
        if (newWeight == null || newWeight <= 0) throw new IllegalArgumentException("Invalid weight");
        e.setWeightKg(newWeight);
        return repo.save(e);
    }

    public void delete(User user, Long id) {
        WeightEntry e = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Entry not found"));
        if (!e.getUser().getId().equals(user.getId())) throw new SecurityException("Not owner");
        repo.delete(e);
    }

    public double diff(User user, LocalDate from, LocalDate to) {
        if (from.isAfter(to)) throw new IllegalArgumentException("from must be <= to");
        List<WeightEntry> entries = repo.findByUserAndEntryDateBetweenOrderByEntryDate(user, from, to);
        if (entries.isEmpty()) throw new IllegalArgumentException("No entries in the range");
        double start = entries.get(0).getWeightKg();
        double end = entries.get(entries.size() - 1).getWeightKg();
        return start - end; // positive => weight loss
    }
}
