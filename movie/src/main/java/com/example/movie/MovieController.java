package com.example.movie;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MovieController {

    @GetMapping("/movie")
    public String moviePage(Model model) {

        String title = "Inception";

        String fullDesc =
                "A thief who steals corporate secrets through dream-sharing technology.\n"
                + "He gets one final job that could restore his old life.";

        // Change this to true to test logged-in output
        boolean isLoggedIn = false;

        model.addAttribute("title", title);
        model.addAttribute("fullDesc", fullDesc);
        model.addAttribute("isLoggedIn", isLoggedIn);

        return "movie";
    }
}
