package com.example.crudRestProjectMVC.restException;

import com.example.crudRestProjectMVC.entity.EmployeeErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EmployeeRestExceptionHandler {

    // add an exception handler that is generic, ie, the one that will catch any exception(catch all)
    @ExceptionHandler
    public ResponseEntity<EmployeeErrorResponse> handleGeneralException(Exception exc){
        EmployeeErrorResponse error = new EmployeeErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }
}
