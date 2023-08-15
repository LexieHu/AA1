package edu.kit.kastel.model;

/**
 * The type of priority for task.
 *
 * @author uyzlh
 * @version 1.0
 */
public enum Priority {
    /**
     * Priority high.
     */
    HI("HI"),
    /**
     * Priority middle.
     */
    MD("MD"),
    /**
     * Priority low.
     */
    LO("LO"),
    /**
     * No priority.
     */
    NONE("");

    private final String label;
    /**
     * Instantiates a new priority type.
     * @param label the string representation of the priority type
     */
    Priority(String label) {
        this.label = label;
    }
    /**
     * Make priority type to a String.
     * @return the string representation of the priority type
     */
    @Override
    public String toString() {
        return label;
    }
    /**
     * Gets the priority type from a string representation.
     * @param str the string representation of the priority type
     * @return the car type
     */
    public static Priority fromString(String str) {
        for (Priority priority : Priority.values()) {
            if (priority.label.equals(str)) {
                return priority;
            }
        }
        return null;
    }
}
