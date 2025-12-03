package com.example.weightloss.controller;

import com.example.weightloss.model.User;
import com.example.weightloss.repository.UserRepository;
import com.example.weightloss.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    private final AuthService authService;
    private final UserRepository userRepository;

    public AuthController(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/weights";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("error", null);
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           Model model) {
        try {
            authService.register(username.trim(), password);
            return "redirect:/login?registered";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return "register";
        }
    }
}
