package com.example.weightloss.service;

import com.example.weightloss.model.User;
import com.example.weightloss.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String username, String rawPassword) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username required");
        }
        if (rawPassword == null || rawPassword.length() < 4) {
            throw new IllegalArgumentException("Password must be at least 4 characters");
        }

        String uname = username.trim();
        if (userRepository.findByUsername(uname).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        User u = new User();
        u.setUsername(uname);
        u.setPassword(passwordEncoder.encode(rawPassword));
        return userRepository.save(u);
    }
}
