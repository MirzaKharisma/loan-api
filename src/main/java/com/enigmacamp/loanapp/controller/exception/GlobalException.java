package com.enigmacamp.loanapp.controller.exception;

import com.enigmacamp.loanapp.model.dto.response.CommonResponse;
import com.enigmacamp.loanapp.utils.exception.AuthenticationException;
import com.enigmacamp.loanapp.utils.exception.ResourceNotFoundException;
import com.enigmacamp.loanapp.utils.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<CommonResponse<String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .data(Optional.empty())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    //TODO: Add more exeption handler
    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<CommonResponse<String>> handleValidationException(ValidationException ex) {
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.NOT_ACCEPTABLE.value())
                .message(ex.getMessage())
                .data(Optional.empty())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<CommonResponse<String>> handleAuthenticationException(AuthenticationException ex) {
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message(ex.getMessage())
                .data(Optional.empty())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
