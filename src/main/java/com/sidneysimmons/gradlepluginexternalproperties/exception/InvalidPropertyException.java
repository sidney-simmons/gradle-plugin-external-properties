package com.sidneysimmons.gradlepluginexternalproperties.exception;

/**
 * Exception for when a requested property name is invalid.
 */
public class InvalidPropertyException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidPropertyException() {
        super();
    }

    public InvalidPropertyException(String message) {
        super(message);
    }

    public InvalidPropertyException(Throwable cause) {
        super(cause);
    }

    public InvalidPropertyException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPropertyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
