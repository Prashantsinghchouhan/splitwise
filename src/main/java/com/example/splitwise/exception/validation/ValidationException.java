package com.example.splitwise.exception.validation;

import com.example.splitwise.exception.DomainException;

public abstract class ValidationException extends DomainException {
    protected ValidationException(String message, String errorCode) {
        super(message, errorCode);
    }
}
