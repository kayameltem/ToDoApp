package com.example.simplespringproject.exception;

/**
 * Exception thrown when a user is not found.
 * <p>
 * This exception is used to indicate that a requested user does not exist in the system.
 * </p>
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }
}