package com.javacodegeeks.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	@GetMapping("hello")
	String hello(@RequestParam(defaultValue = "world!") String name) {
		return "Hello " + name;
	}

}
