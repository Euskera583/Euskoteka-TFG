package com.euskoteka.euskoteka_api.exception;

import org.springframework.http.HttpStatus;

public class EuskotekaException extends RuntimeException {
    private final HttpStatus status;

    public EuskotekaException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
