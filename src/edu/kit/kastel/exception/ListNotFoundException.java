package edu.kit.kastel.exception;

/**
 * Exception thrown when a list with a given name is not found.
 *
 * @author uyzlh
 * @version 1.0
 */
public class ListNotFoundException extends Exception {
    private static final String MESSAGE = "Cannot find list with given list name: %s";

    /**
     * Instantiates a new list not found exception.
     *
     * @param name the list name causing this exception
     */
    public ListNotFoundException(String name) {
        super(MESSAGE.formatted(name));
    }
}
