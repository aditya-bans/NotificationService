package com.notify.notify.exceptions.handler;

import com.notify.notify.exceptions.InValidRequestException;
import com.notify.notify.exceptions.NotFoundException;
import com.notify.notify.model.response.ErrorResponse;
import com.notify.notify.model.response.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(InValidRequestException.class)
    public ResponseEntity handleInValidRequestException(InValidRequestException ex)
    {
        GenericResponse response = new GenericResponse();
        ErrorResponse error = new ErrorResponse();
        error.setCode("INVALID_REQUEST");
        error.setMessage(ex.getMessage());
        response.setError(error);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException(NotFoundException ex)
    {
        GenericResponse response = new GenericResponse();
        ErrorResponse error = new ErrorResponse();
        error.setCode("INVALID_REQUEST");
        error.setMessage(ex.getMessage());
        response.setError(error);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
