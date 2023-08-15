package edu.kit.kastel.exception;

/**
 * Exception thrown when the assign operation is illegal,
 * for example: sign a parent task to its subtask.
 *
 * @author uyzlh
 * @version 1.0
 */
public class IllegalAssignException extends Exception {
    private static final String MESSAGE = "Cannot assign given parent task to a subtask.";

    /**
     * Instantiates a new illegal assign exception.
     */
    public IllegalAssignException() {
        super(MESSAGE.formatted());
    }
}
