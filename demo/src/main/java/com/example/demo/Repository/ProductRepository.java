package com.example.demo.Repository;

import org.springframework.data.repository.CrudRepository;
import com.example.demo.models.ProductModel;

public interface ProductRepository extends CrudRepository<ProductModel,Integer> {

}