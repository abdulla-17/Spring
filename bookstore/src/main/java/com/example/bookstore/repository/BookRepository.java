package com.example.bookstore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.CrudRepository;

import com.example.bookstore.controller.List;
import com.example.bookstore.model.Book;
import com.example.bookstore.models.ProductModel;

public interface BookRepository extends CrudRepository<Book, Long> {

	
}