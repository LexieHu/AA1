package edu.kit.kastel.exception;

/**
 * Exception thrown when a task is deleted.
 *
 * @author uyzlh
 * @version 1.0
 */
public class TaskDeletedException extends Exception {
    private static final String MESSAGE = "Given task is already deleted.";

    /**
     * Instantiates a new task deleted exception.
     */
    public TaskDeletedException() {
        super(MESSAGE.formatted());
    }
}
