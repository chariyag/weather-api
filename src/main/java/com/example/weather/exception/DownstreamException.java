package com.example.weather.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DownstreamException extends RuntimeException{

    @Builder.Default
    private HttpStatus httpStatus= HttpStatus.INTERNAL_SERVER_ERROR;
    private String message;
}
