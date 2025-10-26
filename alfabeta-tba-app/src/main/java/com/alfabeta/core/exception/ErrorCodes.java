package com.alfabeta.core.exception;

/**
 * Centralized list of error codes used across the Alfabeta system.
 *
 * Each error code follows the pattern:
 * ERR_<HTTP_CODE>_<SHORT_DESCRIPTION>
 *
 * This class ensures consistent error tracking and simplifies
 * integration with frontend apps or mobile clients.
 */
public final class ErrorCodes {

    private ErrorCodes() {} // prevent instantiation

    // ==== AUTHENTICATION & ACCESS CONTROL ====
    public static final String ERR_401_UNAUTHORIZED = "ERR_401_UNAUTHORIZED";
    public static final String ERR_403_FORBIDDEN = "ERR_403_FORBIDDEN";
    public static final String ERR_419_SESSION_EXPIRED = "ERR_419_SESSION_EXPIRED";

    // ==== VALIDATION & INPUT ERRORS ====
    public static final String ERR_400_VALIDATION_FAILED = "ERR_400_VALIDATION_FAILED";
    public static final String ERR_400_INVALID_REQUEST = "ERR_400_INVALID_REQUEST";
    public static final String ERR_422_UNPROCESSABLE_ENTITY = "ERR_422_UNPROCESSABLE_ENTITY";

    // ==== RESOURCE MANAGEMENT ====
    public static final String ERR_404_NOT_FOUND = "ERR_404_NOT_FOUND";
    public static final String ERR_409_CONFLICT = "ERR_409_CONFLICT";
    public static final String ERR_410_GONE = "ERR_410_GONE";

    // ==== SERVER & SYSTEM ====
    public static final String ERR_500_INTERNAL_ERROR = "ERR_500_INTERNAL_ERROR";
    public static final String ERR_503_SERVICE_UNAVAILABLE = "ERR_503_SERVICE_UNAVAILABLE";
    public static final String ERR_504_GATEWAY_TIMEOUT = "ERR_504_GATEWAY_TIMEOUT";

    // ==== BUSINESS RULES ====
    public static final String ERR_409_DUPLICATE_ENTRY = "ERR_409_DUPLICATE_ENTRY";
    public static final String ERR_409_INVALID_STATE = "ERR_409_INVALID_STATE";
    public static final String ERR_409_LIMIT_EXCEEDED = "ERR_409_LIMIT_EXCEEDED";

    // ==== FLOW EXECUTION ====
    public static final String ERR_500_FLOW_EXECUTION_FAILED = "ERR_500_FLOW_EXECUTION_FAILED";
    public static final String ERR_500_SCRIPT_ERROR = "ERR_500_SCRIPT_ERROR";

    // ==== DATA ACCESS ====
    public static final String ERR_500_DATABASE_FAILURE = "ERR_500_DATABASE_FAILURE";
    public static final String ERR_409_CONSTRAINT_VIOLATION = "ERR_409_CONSTRAINT_VIOLATION";

    // ==== SECURITY & TOKEN ====
    public static final String ERR_498_INVALID_TOKEN = "ERR_498_INVALID_TOKEN";
    public static final String ERR_499_TOKEN_EXPIRED = "ERR_499_TOKEN_EXPIRED";
}
