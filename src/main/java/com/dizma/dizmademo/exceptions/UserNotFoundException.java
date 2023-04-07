package com.dizma.dizmademo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    private final String message;

    public UserNotFoundException(String message) {
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
