package com.example.demo.AOPexception;

import com.example.demo.AOPexception.Exception.*;
import org.apache.coyote.BadRequestException;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(GetNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(PutDuplicatedException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()),HttpStatus.CONFLICT);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(PutNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(PostIllegalArgumemtException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(PostNotFoundException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(DeleteExistedExcepton e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(RuntimeException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    static class ErrorResponse {
        String message;

        ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
