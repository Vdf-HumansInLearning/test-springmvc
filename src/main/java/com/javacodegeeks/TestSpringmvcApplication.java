package com.javacodegeeks;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.javacodegeeks.domain.Student;
import com.javacodegeeks.service.StudentService;

@SpringBootApplication
public class TestSpringmvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestSpringmvcApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StudentService service) {
	    return args -> {
	        service.save(new Student("Jane", "Doe", "Junior"));
	        service.save(new Student("Martin", "Fowler", "Senior"));
	        service.save(new Student("Roy", "Fielding", "Freshman"));
	    };
	}
}
