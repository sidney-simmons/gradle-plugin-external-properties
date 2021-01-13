package com.sidneysimmons.gradlepluginexternalproperties.exception;

/**
 * Exception for when a property cannot be found.
 */
public class MissingPropertyException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MissingPropertyException() {
        super();
    }

    public MissingPropertyException(String message) {
        super(message);
    }

    public MissingPropertyException(Throwable cause) {
        super(cause);
    }

    public MissingPropertyException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingPropertyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
