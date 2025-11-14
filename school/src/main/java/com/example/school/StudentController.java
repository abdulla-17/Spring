package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StudentController {

   
    public static class Student {
        int rollNo;
        String name;
        double marks;

        public Student(int rollNo, String name, double marks) {
            this.rollNo = rollNo;
            this.name = name;
            this.marks = marks;
        }

        public int getRollNo() { return rollNo; }
        public String getName() { return name; }
        public double getMarks() { return marks; }
    }

    @GetMapping("/student-info")
    public String studentInfo(Model model) {
        model.addAttribute("mode", "info");
        model.addAttribute("student", new Student(101, "Anjali Sharma", 92.5));
        return "student";
    }

    @GetMapping("/student-list")
    public String studentList(Model model) {
        List<Student> list = new ArrayList<>();
        list.add(new Student(101, "Anjali Sharma", 92.5));
        list.add(new Student(102, "Rohit Mehta", 85.0));
        list.add(new Student(103, "Sneha Iyer", 78.6));

        model.addAttribute("mode", "list");
        model.addAttribute("students", list);
        return "student";
    }
}
