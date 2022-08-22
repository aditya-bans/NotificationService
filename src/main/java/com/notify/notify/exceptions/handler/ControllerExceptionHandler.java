package com.notify.notify.exceptions.handler;

import com.notify.notify.model.response.ErrorResponse;
import com.notify.notify.constants.utility.UtilityConstants;
import com.notify.notify.exceptions.InValidRequestException;
import com.notify.notify.exceptions.NotFoundException;
import com.notify.notify.model.response.GenericResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InValidRequestException.class)
    public ResponseEntity handleInValidRequestException(InValidRequestException ex)
    {
        GenericResponse response = new GenericResponse();
        ErrorResponse error = new ErrorResponse();
        error.setCode(UtilityConstants.INVALID_REQUEST);
        error.setMessage(ex.getMessage());
        response.setError(error);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException(NotFoundException ex)
    {
        GenericResponse response = new GenericResponse();
        ErrorResponse error = new ErrorResponse();
        error.setCode(UtilityConstants.INVALID_REQUEST);
        error.setMessage(ex.getMessage());
        response.setError(error);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @Override
    public ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
        GenericResponse response = new GenericResponse();
        ErrorResponse error = new ErrorResponse();
        error.setCode(UtilityConstants.INVALID_REQUEST);
        List<String> messages = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((message) -> {
            messages.add(message.getDefaultMessage());
        });
        error.setMessages(messages);
        response.setError(error);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
