package com.management.system.authservice.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(){
        super("User with this email already exists");
    }
}
