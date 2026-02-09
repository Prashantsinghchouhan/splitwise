package com.example.splitwise.exception;

public class ErrorMessages {

    private ErrorMessages() {}

    // Validation messages
    public static final String USER_NOT_IN_GROUP =
            "User %s is not a member of group %s";

    public static final String INVALID_SPLIT_TYPE =
            "Split type %s is invalid";

    public static final String INVALID_SPLIT_SUM =
            "Split amounts must sum to total expense amount";

    public static final String INVALID_PERCENTAGE_SPLIT =
            "Percentage split must sum to 100";

    // Conflict messages
    public static final String IDEMPOTENCY_KEY_EXPIRED =
            "Idempotency key has expired. Please retry with a new key";

    public static final String DUPLICATE_REQUEST =
            "Duplicate request detected";

    public static final String OPTIMISTIC_LOCK_ERROR =
            "Ledger was updated concurrently. Please retry";

    // Authorization messages
    public static final String UNAUTHORIZED_ACTION =
            "You are not allowed to perform this action";

    //System messages
    public static final String INTERNAL_SERVER_ERROR =
            "Something went wrong";
}
