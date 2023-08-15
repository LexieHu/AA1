package edu.kit.kastel.exception;

/**
 * Exception thrown when a task with a given ID number is not found.
 *
 * @author uyzlh
 * @version 1.0
 */
public class TaskNotFoundException extends Exception {
    private static final String MESSAGE = "Cannot find task with given task ID: %d";
    private static final String TASK_NO_NAME = "Cannot find task with given name: %s%n";

    /**
     * Instantiates a new task not found exception.
     *
     * @param id the task ID causing this exception
     */
    public TaskNotFoundException(int id) {
        super(MESSAGE.formatted(id));
    }

    public TaskNotFoundException(String s) {
        super(TASK_NO_NAME.formatted(s));
    }
}
