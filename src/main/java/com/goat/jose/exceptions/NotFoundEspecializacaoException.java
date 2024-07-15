package com.goat.jose.exceptions;

public class NotFoundEspecializacaoException extends RuntimeException {

    public NotFoundEspecializacaoException(String message) {
        super(message);
    }

    public NotFoundEspecializacaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
