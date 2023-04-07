package com.dizma.dizmademo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException {

    private final Long id;

    private final String message;


    public ProductNotFoundException(String message, Long id) {
        this.id = id;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
