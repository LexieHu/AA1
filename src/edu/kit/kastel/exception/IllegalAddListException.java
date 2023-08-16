package edu.kit.kastel.exception;

/**
 * Exception thrown when add list operation conflict.
 *
 * @author uyzlh
 * @version 1.0
 */
public class IllegalAddListException extends Exception {
    private static final String MESSAGE = "Cannot add a list with given name: %s";

    /**
     * Instantiates a new task not found exception.
     *
     * @param name the name of list causing this exception
     */
    public IllegalAddListException(String name) {
        super(MESSAGE.formatted(name));
    }
}
