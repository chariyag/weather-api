package com.example.weather.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ValidationException extends  RuntimeException{
    @Builder.Default
    private HttpStatus httpStatus= HttpStatus.BAD_REQUEST;
    private String message;
}
