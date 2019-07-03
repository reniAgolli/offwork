package com.example.offwork.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class IncorrectDataException extends RuntimeException {

    public IncorrectDataException(String message) {
        super(message);
    }

    public IncorrectDataException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public IncorrectDataException(Throwable throwable) {
        super(throwable);
    }
}
