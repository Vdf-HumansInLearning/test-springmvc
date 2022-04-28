package com.javacodegeeks.web;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockReset;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.javacodegeeks.domain.Student;
import com.javacodegeeks.service.StudentService;

@WebMvcTest(controllers = StudentRestController.class)
class RestControllerTest {

	MockMvc mockMvc;
	
	@MockBean
	StudentService service;

	private ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	void setUp(WebApplicationContext wac) throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
//						.alwaysExpect(status().isOk())
//						.alwaysExpect(content().contentType(MediaType.APPLICATION_JSON))
						.alwaysDo(print())
						.build();
	}

	@Test
	void whenReadStudent_returnJsonContent() throws Exception {
		Student student = new Student("Bill", "Gates", "Freshman");
		student.setId(102L);
		when(service.findById(102L)).thenReturn(Optional.of(student));

		MvcResult result = mockMvc.perform(get("/students/102")).andReturn();

		Student response = objectMapper.readValue(result.getResponse().getContentAsString(), Student.class);
		assertStudent(student, response);
	}

	@Test
	void whenReadInexistentStudent_returnBadRequest() throws Exception {
		when(service.findById(102L)).thenReturn(Optional.empty());

		mockMvc.perform(get("/students/102"))
			.andExpect(status().isNotFound())
		.andExpect(jsonPath("message").value("Student does not exist"));
	}

	@Test
	void whenReadStudents_returnList() throws Exception {
		Student s1 = new Student("Jane", "Doe", "Junior");
		Student s2 = new Student("Martin", "Fowler", "Senior");
		Student s3 = new Student("Roy", "Fielding", "Freshman");
		List<Student> studentList = List.of(s1, s2, s3);

		when(service.findAll()).thenReturn(studentList);

		MvcResult result = this.mockMvc.perform(get("/students"))
			.andReturn();

		Student[] response = objectMapper.readValue(result.getResponse().getContentAsString(), Student[].class);
		assertStudent(s1, response[0]);
		assertStudent(s2, response[1]);
		assertStudent(s3, response[2]);

		verify(service).findAll();
	}

	private void assertStudent(Student expected, Student actual) {
		Assertions.assertEquals(expected.getFirstName(), actual.getFirstName());
		Assertions.assertEquals(expected.getYear(), actual.getYear());
		Assertions.assertEquals(expected.getLastName(), actual.getLastName());
	}
}