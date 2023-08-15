package edu.kit.kastel.exception;

/**
 * Exception thrown when a task is deleted.
 *
 * @author uyzlh
 * @version 1.0
 */
public class TaskDeletedException extends Exception {
    private static final String MESSAGE = "Given task with ID: %d is deleted.";

    /**
     * Instantiates a new task deleted exception.
     *
     * @param id the task ID causing this exception
     */
    public TaskDeletedException(int id) {
        super(MESSAGE.formatted(id));
    }
}
