package com.example.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.example.bookstore.controller.List;
import com.example.bookstore.model.Book;
import com.example.bookstore.models.ProductModel;

public interface BookRepository extends JpaRepository<Book, Long> {

	
}