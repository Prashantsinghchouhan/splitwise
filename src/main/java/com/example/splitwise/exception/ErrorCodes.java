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
    // Resource not found
    public static final String USER_NOT_FOUND = "USER_NOT_FOUND";
    public static final String GROUP_NOT_FOUND = "GROUP_NOT_FOUND";
    // Conflict
    public static final String USER_ALREADY_EXIST = "USER_ALREADY_EXIST";
    public static final String IDEMPOTENCY_KEY_EXPIRED = "IDEMPOTENCY_KEY_EXPIRED";
    public static final String DUPLICATE_REQUEST = "DUPLICATE_REQUEST";
    public static final String OPTIMISTIC_LOCK = "OPTIMISTIC_LOCK";
    // Authorization
    public static final String UNAUTHORIZED = "UNAUTHORIZED";
    public static final String FORBIDDEN_ACTION = "FORBIDDEN_ACTION";
    // System (used by handler only)
    public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";
}
