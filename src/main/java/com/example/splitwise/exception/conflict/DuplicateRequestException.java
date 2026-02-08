package com.example.splitwise.exception.conflict;

import com.example.splitwise.exception.ErrorCodes;
import com.example.splitwise.exception.ErrorMessages;

public class DuplicateRequestException extends ConflictException{

    protected DuplicateRequestException(String message, String errorCode) {
        super(message, errorCode);
    }
    public static DuplicateRequestException inProgress(){
        return new DuplicateRequestException(ErrorMessages.DUPLICATE_REQUEST, ErrorCodes.DUPLICATE_REQUEST);
    }
}
