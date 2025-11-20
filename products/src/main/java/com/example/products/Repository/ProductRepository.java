package com.example.products.Repository;

import org.springframework.data.repository.CrudRepository;
import com.example.products.Models.ProductModels;

public interface ProductRepository extends CrudRepository<ProductModels, Integer> {
	
}