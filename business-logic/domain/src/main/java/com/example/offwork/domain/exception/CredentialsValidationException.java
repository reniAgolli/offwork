package com.example.offwork.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class CredentialsValidationException extends RuntimeException{
    public CredentialsValidationException(String messsage) {
        super(messsage);
    }

    public CredentialsValidationException(String messsage, Throwable throwable) {
        super(messsage, throwable);
    }

    public CredentialsValidationException(Throwable throwable) {
        super(throwable);
    }
}
