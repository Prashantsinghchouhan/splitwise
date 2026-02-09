package com.example.splitwise.exception.conflict;

import com.example.splitwise.exception.ErrorCodes;
import com.example.splitwise.exception.ErrorMessages;

public class IdempotencyRequestException extends ConflictException{
    protected IdempotencyRequestException(String message, String errorCode) {
        super(message, errorCode);
    }
    public static IdempotencyRequestException expiredIdempotencyKey(){
        return new IdempotencyRequestException(ErrorMessages.IDEMPOTENCY_KEY_EXPIRED, ErrorCodes.IDEMPOTENCY_KEY_EXPIRED);
    }
}
