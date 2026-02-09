package com.example.splitwise.exception.notfound;

import com.example.splitwise.exception.DomainException;

public abstract class ResourceNotFoundException extends DomainException {

    protected ResourceNotFoundException(String errorMessage, String errorCode) {
        super(errorMessage, errorCode);
    }
}