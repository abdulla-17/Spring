package com.example.weightloss.controller;

import com.example.weightloss.model.User;
import com.example.weightloss.model.WeightEntry;
import com.example.weightloss.repository.UserRepository;
import com.example.weightloss.repository.WeightEntryRepository;
import com.example.weightloss.service.WeightService;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/weights")
public class WeightController {

    private final WeightService weightService;
    private final UserRepository userRepository;
    private final WeightEntryRepository weightEntryRepository;

    public WeightController(WeightService weightService,
                            UserRepository userRepository,
                            WeightEntryRepository weightEntryRepository) {
        this.weightService = weightService;
        this.userRepository = userRepository;
        this.weightEntryRepository = weightEntryRepository;
    }

   
    private User currentUser(Principal principal) {
        return userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new IllegalStateException("Authenticated user not found"));
    }

  
    @GetMapping
    public String list(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size,
                       Principal principal,
                       Model model) {
        User u = currentUser(principal);
        Page<WeightEntry> p = weightService.list(u, page, size);
        model.addAttribute("page", p);
        model.addAttribute("currentPage", page);
        return "list";
    }

  
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("error", null);
        return "add";
    }

   
    @PostMapping("/add")
    public String add(@RequestParam Double weightKg, Principal principal, Model model) {
        User u = currentUser(principal);
        try {
            weightService.addWeight(u, weightKg);
            return "redirect:/weights";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return "add";
        }
    }

  
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Principal principal, Model model) {
        User u = currentUser(principal);
        Optional<WeightEntry> opt = weightEntryRepository.findById(id)
                .filter(e -> e.getUser() != null && e.getUser().getId().equals(u.getId()));
        if (opt.isEmpty()) {
            return "redirect:/weights";
        }
        model.addAttribute("entry", opt.get());
        model.addAttribute("error", null);
        return "edit";
    }

  
    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @RequestParam Double weightKg,
                       Principal principal, Model model) {
        User u = currentUser(principal);
        try {
            weightService.edit(u, id, weightKg);
            return "redirect:/weights";
        } catch (Exception ex) {
          
            Optional<WeightEntry> opt = weightEntryRepository.findById(id)
                    .filter(e -> e.getUser() != null && e.getUser().getId().equals(u.getId()));
            opt.ifPresent(e -> model.addAttribute("entry", e));
            model.addAttribute("error", ex.getMessage());
            return "edit";
        }
    }

  
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Principal principal) {
        User u = currentUser(principal);
        try {
            weightService.delete(u, id);
        } catch (Exception ignored) {
            
        }
        return "redirect:/weights";
    }

 
    @GetMapping("/diff")
    public String diff(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Principal principal,
            Model model) {

        User u = currentUser(principal);

        if (from == null || to == null) {
            model.addAttribute("diffError", "Both from and to dates are required.");
        } else if (from.isAfter(to)) {
            model.addAttribute("diffError", "'From' date must be earlier than or equal to 'To' date.");
        } else {
            try {
                double diffValue = weightService.diff(u, from, to);
                model.addAttribute("diff", diffValue);
            } catch (IllegalArgumentException ex) {
                model.addAttribute("diffError", ex.getMessage());
            } catch (Exception ex) {
                model.addAttribute("diffError", "An error occurred: " + ex.getMessage());
            }
        }

    
        Page<WeightEntry> p = weightService.list(u, page, size);
        model.addAttribute("page", p);
        model.addAttribute("currentPage", page);

        return "list";
    }
}
