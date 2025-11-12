package com.example.photography;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PhotographyController {

    @GetMapping("/start")
    public String redirectToWelcome() {
        return "redirect:/welcome";
    }

    @GetMapping("/welcome")
    public String showWelcomePage() {
        return "welcome";
    }
}
