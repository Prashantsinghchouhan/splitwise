package com.example.splitwise.exception.authorisation;


import lombok.Getter;

@Getter
public abstract class AuthorisationException extends RuntimeException {
    private final String errorCode;

    protected AuthorisationException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
