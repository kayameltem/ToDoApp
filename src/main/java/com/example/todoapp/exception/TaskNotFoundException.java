package com.example.todoapp.exception;

/**
 * Exception thrown when a task is not found.
 * <p>
 * This exception is used to indicate that a requested task does not exist in the system.
 * </p>
 */
public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException() {
        super();
    }

    public TaskNotFoundException(String message) {
        super(message);
    }

    public TaskNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskNotFoundException(Throwable cause) {
        super(cause);
    }
}