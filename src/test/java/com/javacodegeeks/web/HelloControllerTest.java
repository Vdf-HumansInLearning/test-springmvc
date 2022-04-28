package com.javacodegeeks.web;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class HelloControllerTest {
	
	MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();
	}

	@Test
	void whenNoRequestParam_returnDefault() throws Exception {
		this.mockMvc.perform(get("/hello"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Hello world!")))
			.andDo(print());
	}
}