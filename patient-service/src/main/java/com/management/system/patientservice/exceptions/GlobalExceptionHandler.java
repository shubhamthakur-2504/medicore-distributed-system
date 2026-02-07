package com.management.system.patientservice.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException errs,
                                                                   HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();

        errs.getBindingResult().getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

        errs.getBindingResult().getGlobalErrors().forEach(err ->
                errors.put(err.getObjectName(), err.getDefaultMessage()));

        String mainMessage;
        if (errors.size() == 1) {
            mainMessage = errors.values().iterator().next();
        } else {
            mainMessage = "Validation failed: " + errors.size() + " errors found";
        }

        return buildErrorResponse(HttpStatus.BAD_REQUEST, mainMessage, request, errors);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException err
            , HttpServletRequest request) {
        Map<String, String> error = new HashMap<>();
        error.put("email", err.getMessage());
        return buildErrorResponse(HttpStatus.CONFLICT, err.getMessage(), request, error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException err,
                                                                         HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, err.getMessage(), request, null);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message,
                                                             HttpServletRequest request, Map<String, String> error){
        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                message,
                request.getRequestURI(),
                error
        );
        return new ResponseEntity<>(response,status);
    }
}
