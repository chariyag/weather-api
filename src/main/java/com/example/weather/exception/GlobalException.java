package com.example.weather.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException validationException, WebRequest request) {
       return new ResponseEntity(ErrorDto.builder().message(validationException.getMessage()).build(),HttpStatus.TOO_MANY_REQUESTS);
    }
    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult result = ex.getBindingResult();
        List<String> fieldErrors = result.getFieldErrors().stream()
                .map(f ->  f.getField())
                .collect(Collectors.toList());
        return new ResponseEntity<>(ErrorDto.builder().errors(fieldErrors).message("required fields missing").build(),HttpStatus.BAD_REQUEST);

    }

}
