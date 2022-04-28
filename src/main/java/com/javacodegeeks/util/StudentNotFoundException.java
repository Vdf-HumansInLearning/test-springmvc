package com.javacodegeeks.util;

public class StudentNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public StudentNotFoundException() {
        super("Student does not exist");
    }
}
