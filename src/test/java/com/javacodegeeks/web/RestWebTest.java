package com.javacodegeeks.web;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.javacodegeeks.domain.Student;
import com.javacodegeeks.service.StudentService;
@WebMvcTest(controllers = StudentRestController.class)
class RestWebTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	StudentService service;
	
	@Test
	void whenReadStudent_returnJsonContent() throws Exception {
		Student student = new Student("Bill", "Gates", "Freshman");
		student.setId(102L);
		
		when(service.findById(102L)).thenReturn(Optional.of(student));
		

		this.mockMvc.perform(get("/students/102"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString(
				"{\"id\":102,\"firstName\":\"Bill\",\"lastName\":\"Gates\",\"year\":\"Freshman\"}")))
			.andDo(print());
	}

	@Test
	void whenAddStudent_returnCreatedStatus() throws Exception {
		Student newStudent = new Student("Bill", "Gates", "Freshman");
		newStudent.setId(100L);

		reset(service);
		when(service.save(newStudent)).thenReturn(newStudent);

		
		this.mockMvc
			.perform(post("/students")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\": \"100\",\"firstName\": \"Bill\",\"lastName\": \"Gates\",\"year\": \"Freshman\"}")
				)
			.andExpect(status().isCreated())
			.andExpect(header().exists("Location"))
			.andExpect(header().string("Location", Matchers.containsString(
					"http://localhost/students/100"))).andDo(print());
	}
	
}
