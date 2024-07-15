package com.goat.jose.exceptions;

public class NotFoundServidorException extends RuntimeException {

    public NotFoundServidorException(String message) {
        super(message);
    }

    public NotFoundServidorException(String message, Throwable cause) {
        super(message, cause);
    }
}
