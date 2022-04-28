package com.javacodegeeks.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
 
import java.util.List;
 
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
 
import com.javacodegeeks.domain.Student;
import com.javacodegeeks.service.StudentService;
 
@WebMvcTest(controllers = StudentMvcController.class)
class MvcWebTest {
 
    @Autowired
    MockMvc mockMvc;
 
    @MockBean
    StudentService service;
 
    @Test
    void shouldReturnStudentListView() throws Exception {
        Student s1 = new Student("Jane", "Doe", "Junior");
        Student s2 = new Student("Martin", "Fowler", "Senior");
        Student s3 = new Student("Roy", "Fielding", "Freshman");
        List<Student> studentList = List.of(s1, s2, s3);
 
        when(service.findAll()).thenReturn(studentList);
 
        this.mockMvc.perform(get("/mvc/students"))
            .andExpect(status().isOk())
            .andExpect(view().name("student-list"))
            .andExpect(model().attribute("students", studentList))
            .andExpect(model().attribute("students", Matchers.hasSize(3)))
            .andDo(print());
    }
 
}