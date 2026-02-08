package com.example.splitwise.exception.validation;

import com.example.splitwise.exception.DomainException;
import com.example.splitwise.exception.ErrorCodes;
import com.example.splitwise.exception.ErrorMessages;

public class UserNotInGroupException extends ValidationException {
    protected UserNotInGroupException(String message, String errorCode) {
        super(message, errorCode);
    }

    public static UserNotInGroupException userNotInGroup(String userId, String groupId){
        return new UserNotInGroupException(ErrorMessages.USER_NOT_IN_GROUP, ErrorCodes.USER_NOT_IN_GROUP);
    }
}