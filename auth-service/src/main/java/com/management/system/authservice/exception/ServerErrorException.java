package com.management.system.authservice.exception;

public class ServerErrorException extends RuntimeException {
    public ServerErrorException() {
        super("Something went wrong");
    }
}
