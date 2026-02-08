package com.example.splitwise.exception.conflict;

import com.example.splitwise.exception.ErrorCodes;
import com.example.splitwise.exception.ErrorMessages;

public class ConcurrentUpdateException extends ConflictException{
    protected ConcurrentUpdateException(String message, String errorCode) {
        super(message, errorCode);
    }

    public static ConcurrentUpdateException optimisticLockException(){
        return new ConcurrentUpdateException(ErrorMessages.OPTIMISTIC_LOCK_ERROR, ErrorCodes.OPTIMISTIC_LOCK);
    }
}
