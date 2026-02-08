package com.example.splitwise.exception.validation;

import com.example.splitwise.enums.SplitType;
import com.example.splitwise.exception.ErrorCodes;
import com.example.splitwise.exception.ErrorMessages;

public class InvalidSplitException extends ValidationException{
    protected InvalidSplitException(String message, String errorCode) {
        super(message, errorCode);
    }

    public static InvalidSplitException  sumMismatch(){
        return new InvalidSplitException(ErrorMessages.INVALID_SPLIT_SUM, ErrorCodes.INVALID_SPLIT);
    }

    public static InvalidSplitException percentMismatch(){
        return new InvalidSplitException(ErrorMessages.INVALID_PERCENTAGE_SPLIT, ErrorCodes.INVALID_SPLIT);
    }

    public static InvalidSplitException invalidSplitType(SplitType splitType){
        return new InvalidSplitException(ErrorMessages.INVALID_SPLIT_TYPE, ErrorCodes.INVALID_SPLIT);
    }
}
