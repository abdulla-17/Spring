package com.example.weightloss.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDbError(DataIntegrityViolationException ex, Model model) {
        model.addAttribute("error", "Conflict: " + ex.getMostSpecificCause().getMessage());
        return "error";
    }

    @ExceptionHandler({ IllegalArgumentException.class, IllegalStateException.class, SecurityException.class })
    public String handleBadRequest(Exception ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "error";
    }
}
