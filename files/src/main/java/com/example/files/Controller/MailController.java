package com.example.files.Controller;

import com.example.files.Models.ProductModel;
import com.example.files.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MailController {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private ProductRepository productRepository;

    // Send email with product details
    @GetMapping("/email/{id}")
    @ResponseBody
    public String sendEmail(@PathVariable Integer id) {
        try {
            // Fetch product from the database
            ProductModel product = productRepository.findById(id).orElse(null);
            
            if (product == null) {
                return "Product not found!";
            }

            // Prepare the email content
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo("receiver@mailtrap.io");  // Replace with your Mailtrap address or receiver's email
            msg.setSubject("Product Details: " + product.getName());
            msg.setText("Product Name: " + product.getName() + "\n"
                    + "Description: " + product.getDescription() + "\n"
                    + "Price: " + product.getPrice() + "\n"
                    + "Expiry Date: " + product.getExpirydate());

            // Send the email
            sender.send(msg);
            return "Successfully sent email for product: " + product.getName();
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
            return "Sending email failed";
        }
    }
}