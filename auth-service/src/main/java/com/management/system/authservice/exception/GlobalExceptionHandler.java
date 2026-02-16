package com.management.system.authservice.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorResponse> handleSignatureException(SignatureException err,
                                                                  HttpServletRequest request){
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, err.getMessage(), request, null);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpireJwtException(ExpiredJwtException err,
                                                                  HttpServletRequest request){
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, err.getMessage(), request, null);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> handleJwtException(JwtException err, HttpServletRequest request){
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, err.getMessage(), request, null);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException err,
                                                                           HttpServletRequest request){
        return buildErrorResponse(HttpStatus.CONFLICT, err.getMessage(), request, null);
    }

    @ExceptionHandler(InvalidData.class)
    public ResponseEntity<ErrorResponse> handleInvalidDataException(InvalidData err,
                                                                    HttpServletRequest request){
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, err.getMessage(), request, null);
    }

    @ExceptionHandler(ServerErrorException.class)
    public ResponseEntity<ErrorResponse> handleServerErrorException(ServerErrorException err,
                                                                    HttpServletRequest request){
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, err.getMessage(), request, null);
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
