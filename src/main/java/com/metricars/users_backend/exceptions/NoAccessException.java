package com.metricars.users_backend.exceptions;

public class NoAccessException extends RuntimeException {
    public NoAccessException() {
        super("Can't access to resource");
    }
}
