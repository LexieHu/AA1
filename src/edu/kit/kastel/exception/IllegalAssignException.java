package edu.kit.kastel.exception;

/**
 * Exception thrown when assign a task conflict.
 *
 * @author uyzlh
 * @version 1.0
 */
public class IllegalAssignException extends Exception {
    private static final String ILLEGAL_ASSIGN_OPERATION = "Cannot assign given task.";
    private static final String ASSIGN_TASK_TO_ITSELF = "Cannot assign given task with ID: %d to itself.";
    private static final String TASK_ALREADY_ASSIGNED_ERROR = "Given task is already assigned in list: %s";

    /**
     * Instantiates a new illegal assign exception.
     *
     */
    public IllegalAssignException() {
        super(ILLEGAL_ASSIGN_OPERATION.formatted());
    }

    /**
     * Instantiates a new illegal assign exception.
     *
     * @param id the id of task
     */
    public IllegalAssignException(int id) {
        super(ASSIGN_TASK_TO_ITSELF.formatted(id));
    }

    /**
     * Instantiates a new illegal assign exception.
     *
     * @param s the name of a list
     */
    public IllegalAssignException(String s) {
        super(TASK_ALREADY_ASSIGNED_ERROR.formatted(s));
    }
}
