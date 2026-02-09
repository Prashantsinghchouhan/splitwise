package com.example.splitwise.exception.notfound;

import com.example.splitwise.exception.ErrorCodes;
import com.example.splitwise.exception.ErrorMessages;

public class GroupNotFoundException extends ResourceNotFoundException{
    protected GroupNotFoundException(String errorMessage, String errorCode) {
        super(errorMessage, errorCode);
    }

    public static GroupNotFoundException groupNotFound(String userId){
        return new GroupNotFoundException(ErrorMessages.GROUP_NOT_FOUND, ErrorCodes.GROUP_NOT_FOUND);
    }
}
