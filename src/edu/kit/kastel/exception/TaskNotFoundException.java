package edu.kit.kastel.exception;

public class TaskNotFoundException extends Exception {
    private static final String MESSAGE = "Cannot find task with given task ID: %d";

    /**
     * Instantiates a new station not found exception.
     */
    public TaskNotFoundException(int id) {
        super(MESSAGE.formatted(id));
    }
}
