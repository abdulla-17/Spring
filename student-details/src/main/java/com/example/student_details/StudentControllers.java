package com.example.student_details;

import org.springframework.web.bind.annotation.GetMapping;
import com.example.myapp.Models.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class StudentController {
	@GetMapping("/student.info")
	public String studentinfo(Model model)
	StudentModels
}