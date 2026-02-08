package com.example.splitwise.exception;

/*
This class cannot be extended.
Cannot be changed after load
 */
public final class ErrorCodes {
    private ErrorCodes() {}

    // Validation
    public static final String INVALID_SPLIT = "INVALID_SPLIT";
    public static final String USER_NOT_IN_GROUP = "USER_NOT_IN_GROUP";
    // Conflict
    public static final String DUPLICATE_REQUEST = "DUPLICATE_REQUEST";
    // Authorization
    public static final String UNAUTHORIZED_ACTION = "UNAUTHORIZED_ACTION";
    // System (used by handler only)
    public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";
}
