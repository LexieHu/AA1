package edu.kit.kastel.exception;

/**
 * Exception thrown when a tag is already used.
 *
 * @author uyzlh
 * @version 1.0
 */
public class TagAlreadyUsedException extends Exception {
    private static final String MESSAGE = "Given tag %s is already used.";

    /**
     * Instantiates a new tag already used exception.
     *
     * @param s tag of a task or a list
     */
    public TagAlreadyUsedException(String s) {
        super(MESSAGE.formatted(s));
    }
}
