package com.javacodegeeks.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javacodegeeks.service.StudentService;

@Controller
@RequestMapping("/mvc")
public class StudentMvcController {
	
	private final StudentService service;
	
	public StudentMvcController(StudentService service) {
		this.service = service;
	}
	
	@GetMapping("/students")
	public String getStudents(Model model) {
		model.addAttribute("students", service.findAll());
		return "student-list";
	}

}
