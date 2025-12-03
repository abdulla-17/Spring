package com.example.files.Repository;

import org.springframework.data.repository.CrudRepository;
import com.example.files.Models.ProductModel;

public interface ProductRepository extends CrudRepository<ProductModel,Integer> {

}