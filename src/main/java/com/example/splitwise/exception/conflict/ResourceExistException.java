package com.example.splitwise.exception.conflict;

public class ResourceExistException extends ConflictException{
    public ResourceExistException(String message, String errorCode) {
        super(message, errorCode);
    }
}
