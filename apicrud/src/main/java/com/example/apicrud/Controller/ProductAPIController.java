package com.example.apicrud.Controller;

import com.example.apicrud.exception.ProductNotFoundException;
import com.example.apicrud.Models.Product;
import com.example.apicrud.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@CrossOrigin("http://localhost:3002")
public class ProductAPIController {


    @Autowired
    private ProductRepository productRepository;


    @PostMapping("api/addproduct")
    Product newProduct(@RequestBody Product newProduct) {
        return productRepository.save(newProduct);
    }


    @GetMapping("api/listproduct")
    List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    @GetMapping("api/product/{id}")
    Product getProductById(@PathVariable Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }


    @PutMapping("api/updateproduct/{id}")
    Product updateProduct(@RequestBody Product newProduct, @PathVariable Integer id) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(newProduct.getName());
                    product.setDescription(newProduct.getDescription());
                    product.setPrice(newProduct.getPrice());
                    product.setExpirydate(newProduct.getExpirydate());
                    return productRepository.save(product);
                }).orElseThrow(() -> new ProductNotFoundException(id));
    }

    @DeleteMapping("api/deleteproduct/{id}")
    String deleteProduct(@PathVariable Integer id){
        if(!productRepository.existsById(id)){
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
        return  "Product with id "+id+" has been deleted success.";
    }
    
     @GetMapping("api/search")
        List<Product> searchProducts(@Param("keyword") String keyword) {
            return productRepository.findAllByKeyword(keyword);
        }
}