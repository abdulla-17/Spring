package com.example.bookstore.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;


@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/books/new")
    public String showBookForm(Model model) {
        model.addAttribute("book", new Book()); // ✅ Correct: Book object
        return "book_form";
    }

    @PostMapping("/books/save")
    public String saveBook(@ModelAttribute Book book) {
        bookRepository.save(book);
        return "redirect:/books";
    }

    @GetMapping("/books")
    public String listBooks(model model) {
        List books = (List) bookRepository.findAll();
        model.addAttribute("books", books);
        return "book_list";
    }
}