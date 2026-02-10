package com.example.splitwise.exception.authorisation;

import com.example.splitwise.exception.ErrorCodes;
import com.example.splitwise.exception.ErrorMessages;

public class UnauthorizedException extends AuthorisationException {

    public UnauthorizedException(String message, String errorCode) {
        super(message, errorCode);
    }

    public static UnauthorizedException missingUserHeader() {
        return new UnauthorizedException(ErrorMessages.MISSING_USER_ID_HEADER, ErrorCodes.UNAUTHORIZED);
    }
}