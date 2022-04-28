package com.javacodegeeks.util;

public class StudentIdGenerator {

    private static long id = 100;

    public static synchronized long value() {
        return id++;
    }
}
