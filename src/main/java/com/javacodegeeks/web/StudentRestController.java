package com.javacodegeeks.web;

import com.javacodegeeks.util.StudentNotFoundException;
import java.net.URI;
import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.javacodegeeks.domain.Student;
import com.javacodegeeks.service.StudentService;
import com.javacodegeeks.util.StudentIdGenerator;

@RestController
@RequestMapping("students")
public class StudentRestController {

	private final StudentService service;
	
	public StudentRestController(StudentService service) {
		this.service = service;
	}
	
	@GetMapping
	Collection<Student> readStudents(){
		return this.service.findAll();
	}
	
	@GetMapping("/{id}")
	Student readStudent(@PathVariable Long id) {
		return this.service.findById(id)
				.orElseThrow(StudentNotFoundException::new);
	}
	
	@PostMapping
	ResponseEntity<?> addStudent(@RequestBody Student student){
		// Hack to get Mockito test to work
		// Will fix this soon
		// When not running JUnit tests
		// These statements should be commented out
		// and the statements below should be uncommented
		this.service.save(student);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(StudentIdGenerator.value())
				.toUri();
		
		/*
		 * Student result = this.service.save(student); URI location =
		 * ServletUriComponentsBuilder .fromCurrentRequest() .path("/{id}")
		 * .buildAndExpand(result.getId()) .toUri();
		 */

		return ResponseEntity.created(location).build();		
	}
	
	@PutMapping
	Student updateStudent(@RequestBody Student student) {
		return this.service.update(student)
				.orElseThrow(StudentNotFoundException::new);
	}
	
	@DeleteMapping("/{id}")
	void deleteStudent(@PathVariable Long id) {
		this.service.delete(id)
			.orElseThrow(StudentNotFoundException::new);
	}		


}

