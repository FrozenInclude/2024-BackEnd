package com.example.demo.AOPexception;

import com.example.demo.AOPexception.Exception.*;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
