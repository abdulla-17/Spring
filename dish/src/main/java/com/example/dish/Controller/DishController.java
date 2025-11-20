package com.example.dish.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Controller
public class DishController {

    
    @GetMapping("/dish/{name}/{price}")
    public String showDish(
            @PathVariable("name") String rawName,
            @PathVariable("price") BigDecimal price,
            Model model) {

   
        String name = URLDecoder.decode(rawName, StandardCharsets.UTF_8);

        model.addAttribute("dishName", name);
        model.addAttribute("dishPrice", price);

    
        return "dish";
    }
}
