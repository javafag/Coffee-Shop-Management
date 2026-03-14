package com.example.Coffee.Shop.Management.exception;

import com.example.Coffee.Shop.Management.dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponseDto> handleException(ResponseStatusException ex, HttpServletRequest request){
        if(ex.getStatusCode() == HttpStatus.NOT_FOUND) {

            ErrorResponseDto response = ErrorResponseDto
                    .builder()
                    .status(404)
                    .code("NOT_FOUND_ERROR")
                    .msg("This order doesn't exist")
                    .path(request.getRequestURI())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        throw ex;
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleException(MethodArgumentNotValidException ex, HttpServletRequest request)  {
        Map<String,String> fieldErrors = new HashMap<>();

        for(FieldError fe : ex.getBindingResult().getFieldErrors()){
            fieldErrors.put(fe.getField(),fe.getDefaultMessage());
        }

            ErrorResponseDto response = ErrorResponseDto
                    .builder()
                    .status(400)
                    .code("VALIDATION_ERROR")
                    .msg("Validation failed")
                    .path(request.getRequestURI())
                    .fieldErrors(fieldErrors)
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);


    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleAllUncaughtExceptions(Exception ex, HttpServletRequest request) {
        ex.printStackTrace();
        ErrorResponseDto response = ErrorResponseDto.builder()
                .status(500)
                .code("INTERNAL_SERVER_ERROR")
                .msg("Something went wrong on our side")
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
