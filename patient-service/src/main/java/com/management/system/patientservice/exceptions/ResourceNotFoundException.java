package com.management.system.patientservice.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    private final String resourceType;
    public ResourceNotFoundException(String message, String resourceType) {
        super(message);
        this.resourceType = resourceType;
    }

    public String getResourceType() {
        return resourceType;
    }
}
