package com.example.splitwise.exception.authorisation;

public class ForbiddenException extends AuthorisationException{
    public ForbiddenException(String message, String errorCode) {
        super(message, errorCode);
    }
}
