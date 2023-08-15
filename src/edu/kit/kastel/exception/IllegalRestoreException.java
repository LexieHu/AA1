package edu.kit.kastel.exception;

/**
 * Exception thrown when restore a task conflict.
 *
 * @author uyzlh
 * @version 1.0
 */
public class IllegalRestoreException extends Exception {
    private static final String ILLEGAL_ASSIGN_OPERATION_ERROR = "Cannot restore given task with ID: %d";

    /**
     * Instantiates a new illegal assign exception.
     *
     * @param id a task ID
     */
    public IllegalRestoreException(int id) {
        super(ILLEGAL_ASSIGN_OPERATION_ERROR.formatted(id));
    }
}

