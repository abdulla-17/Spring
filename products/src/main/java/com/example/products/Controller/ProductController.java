package com.example.products.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.products.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.example.products.Models.ProductModels;

@Controller
public class ProductController {
	@GetMapping("/product")
	public String Product(Model model) {
		model.addAttribute("message", "Enter product details");
		return "Product";
	}
	
	@Autowired
	private ProductRepository productrepository;
	
	@PostMapping("/save-product")
	public String Product(ProductModels productData, Model model) {
		ProductModels n = new ProductModels();
		n.setName(productData.getName());
		n.setDescription(productData.getDescription());
		n.setPrice(productData.getPrice());
		productrepository.save(n);
		
		model.addAttribute("message", "Product added:" + productData.getName());
		return "Product";
	}
}