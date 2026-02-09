package com.example.splitwise.exception.notfound;

import com.example.splitwise.exception.ErrorCodes;
import com.example.splitwise.exception.ErrorMessages;

public class UserNotFoundException extends ResourceNotFoundException {
    protected UserNotFoundException(String message, String errorCode) {
        super(message, errorCode);
    }

    public static UserNotFoundException userNotFound(String userId){
        return new UserNotFoundException(ErrorMessages.USER_NOT_FOUND, ErrorCodes.USER_NOT_FOUND);
    }
}
