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

    // helper to fetch current user entity
    private User currentUser(Principal principal) {
        return userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new IllegalStateException("Authenticated user not found"));
    }

    /**
     * List weights (paged). Renders list.html which expects "page" and "currentPage".
     */
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

    /**
     * Show add form
     */
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("error", null);
        return "add";
    }

    /**
     * Handle add request (adds today's weight). Shows error in add.html on conflict/validation.
     */
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

    /**
     * Show edit form for a specific entry id (ensures ownership).
     */
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

    /**
     * Handle edit submission.
     */
    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @RequestParam Double weightKg,
                       Principal principal, Model model) {
        User u = currentUser(principal);
        try {
            weightService.edit(u, id, weightKg);
            return "redirect:/weights";
        } catch (Exception ex) {
            // re-show form with error
            Optional<WeightEntry> opt = weightEntryRepository.findById(id)
                    .filter(e -> e.getUser() != null && e.getUser().getId().equals(u.getId()));
            opt.ifPresent(e -> model.addAttribute("entry", e));
            model.addAttribute("error", ex.getMessage());
            return "edit";
        }
    }

    /**
     * Delete an entry (POST). Ownership enforced in service.
     */
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Principal principal) {
        User u = currentUser(principal);
        try {
            weightService.delete(u, id);
        } catch (Exception ignored) {
            // ignore and redirect back
        }
        return "redirect:/weights";
    }

    /**
     * Calculate difference between two dates and render the list page with the result.
     * Shows friendly errors in the same view if something is wrong.
     */
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

        // always populate the listing so list.html can render
        Page<WeightEntry> p = weightService.list(u, page, size);
        model.addAttribute("page", p);
        model.addAttribute("currentPage", page);

        return "list";
    }
}
