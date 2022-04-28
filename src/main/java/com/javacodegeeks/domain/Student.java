package com.javacodegeeks.domain;

import java.util.Objects;

public class Student {

    private Long id;
    private String firstName;
    private String lastName;
    private String year;

	public Student() {
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, firstName, lastName, year);
	}

	public Student(String firstName, String lastName, String year) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.year = year;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
    
    
}
