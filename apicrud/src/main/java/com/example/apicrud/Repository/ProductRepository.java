package com.example.apicrud.Repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.apicrud.Models.ProductModel;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {
	
	 @Query("SELECT p FROM ProductModel p WHERE p.name LIKE %:keyword% OR p.description LIKE %:keyword%")
	    List<ProductModel> findAllByKeyword(@Param("keyword") String keyword);

	 Optional<ProductModel> findById(Integer id);

	 boolean existsById(Integer id);

	 void deleteById(Integer id);
	 


}