package com.example.splitwise.api.model.responsedto;

import com.example.splitwise.exception.DomainException;
import com.example.splitwise.exception.ErrorCodes;
import com.example.splitwise.exception.ErrorMessages;
import lombok.Getter;

@Getter
public class ErrorResponse {
    private final String errorCode;
    private final String message;
    public ErrorResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public static ErrorResponse of(DomainException ex){
        return new ErrorResponse(ex.getErrorCode(), ex.getMessage());
    }

    public static ErrorResponse system(Exception ex){
        return new ErrorResponse(ErrorCodes.INTERNAL_SERVER_ERROR, ErrorMessages.INTERNAL_SERVER_ERROR);
    }

}
