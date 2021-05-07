package com.metricars.users_backend.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String entity) {
        super(String.format("%s not found", entity));
    }
}
