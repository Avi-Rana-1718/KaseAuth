package com.avirana.config;

import com.avirana.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorDto> responseStatusException(ResponseStatusException ex) {
        return new ResponseEntity<>(new ErrorDto(ex.getMessage()), ex.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> genericException(Exception ex) {
        return new ResponseEntity<>(new ErrorDto(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
