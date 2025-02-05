package org.example.eduapp.exceptions;

import org.example.eduapp.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"unchecked", "rawtypes"})
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler()
    public ApiResponse response(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ApiResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("VALIDATION ERROR")
                .errors(errors)
                .build();
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ApiResponse resourceNotFoundException(ResourceNotFoundException e) {
        return ApiResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .build();
    }

    @ExceptionHandler(value = {BadRequestException.class})
    public ApiResponse badRequestException(BadRequestException e) {
        return ApiResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }
}
