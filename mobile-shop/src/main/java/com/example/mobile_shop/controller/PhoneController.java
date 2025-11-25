package com.example.mobile_shop.controller;

import com.example.mobile_shop.model.Phone;
import com.example.mobile_shop.repository.PhoneRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/phones")
public class PhoneController {

    private final PhoneRepository repo;

    public PhoneController(PhoneRepository repo) {
        this.repo = repo;
    }

    
    @GetMapping
    public String listPhones(Model model) {
        model.addAttribute("phones", repo.findAll());
        return "phones_list";
    }

    
    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("phone", new Phone());
        return "phone_form";
    }

    
    @PostMapping("/save")
    public String savePhone(@ModelAttribute Phone phone, RedirectAttributes ra) {
        if (phone.getPrice() == null) phone.setPrice(BigDecimal.ZERO);
        if (phone.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            ra.addFlashAttribute("error", "Price cannot be negative");
            return "redirect:/phones";
        }
        repo.save(phone);
        ra.addFlashAttribute("success", "Phone saved successfully");
        return "redirect:/phones";
    }

    
    @GetMapping("/edit/{id}")
    public String editPhone(@PathVariable Long id, Model model, RedirectAttributes ra) {
        Optional<Phone> opt = repo.findById(id);
        if (opt.isEmpty()) {
            ra.addFlashAttribute("error", "Phone not found");
            return "redirect:/phones";
        }
        model.addAttribute("phone", opt.get());
        return "phone_form";
    }

    
    @GetMapping("/delete/{id}")
    public String deletePhone(@PathVariable Long id, RedirectAttributes ra) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            ra.addFlashAttribute("success", "Phone deleted");
        } else {
            ra.addFlashAttribute("error", "Phone not found");
        }
        return "redirect:/phones";
    }
}
