package com.example.myapp.Repository;

import org.springframework.data.repository.CrudRepository;
import com.example.myapp.Models.ProductModel;

public interface ProductRepository extends CrudRepository<ProductModel,Integer> {

}