package com.example.splitwise.exception.conflict;

import com.example.splitwise.exception.DomainException;

public  abstract class ConflictException extends DomainException {
    protected ConflictException(String message, String errorCode) {
        super(message, errorCode);
    }
}
