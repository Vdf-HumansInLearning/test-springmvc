package com.javacodegeeks.web;

import com.javacodegeeks.domain.Error;
import com.javacodegeeks.util.StudentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class StudentApiExceptionHandler {

    @ExceptionHandler(StudentNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleException(StudentNotFoundException exception) {
        return new Error(exception.getMessage());
    }

}
