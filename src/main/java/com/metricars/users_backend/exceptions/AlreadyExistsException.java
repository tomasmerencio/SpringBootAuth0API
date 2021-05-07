package com.metricars.users_backend.exceptions;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String entity) {
        super(String.format("%s already exists", entity));
    }
}
