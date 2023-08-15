package edu.kit.kastel.exception;

/**
 * Exception thrown when no tasks found in the system.
 *
 * @author uyzlh
 * @version 1.0
 */
public class NoTaskFoundException extends Exception {
    private static final String MESSAGE = "No tasks found.";

    /**
     * Instantiates a new no task found exception.
     */
    public NoTaskFoundException() {
        super(MESSAGE.formatted());
    }
}
