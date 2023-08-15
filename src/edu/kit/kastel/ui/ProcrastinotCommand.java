package edu.kit.kastel.ui;

import edu.kit.kastel.model.Procrastinot;
import java.util.Objects;

/**
 * The command handler responsible for maintaining all neccessary commands and user interaction.
 *
 * @author uyzlh
 * @version 1.0
 */
public abstract class ProcrastinotCommand extends Command {
    /**
     * Regex describing a valid name for all name regarding placeholders
     */
    protected static final String NAME_REGEX = "^\\S+$";
    /**
     * Regex describing the date format yyyy-MM-dd
     */
    protected static final String DATE_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";
    /**
     * Regex describing the priority format 
     */
    protected static final String PRIORITY_REGEX = "^(HI|MD|LO)?$";
    /**
     * Regex describing the priority format tarting at 1 and then increasing
     */
    protected static final String ID_REGEX = "^[1-9]\\d*$";
    /**
     * Regex describing the tag format 
     */
    protected static final String TAG_REGEX = "^[A-Za-z0-9]+$";
    /**
     * Regex describing the list name format 
     */
    protected static final String LIST_NAME_REGEX = "^[A-Za-z]+$";
    /**
     * Pattern for the date format
     */
    protected static final String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * Pattern format of the junction of date and time
     */
    protected static final String DATE_PATTERN_FFORMAT = "%s";
    /**
     * Error message if the ID does not correspond to a task
     */
    protected static final String ID_NOT_FOUND_ERROR = createError("Cannot find task with given task ID.");
    /**
     * Error message if the list name does not exist
     */
    protected static final String LIST_NOT_FOUND_ERROR
            = createError("Cannot find list with given list name.");
    /**
     * Error message if the task name is invalid
     */
    protected static final String INVALID_TASK_NAME_ERROR = createError("Given task name is invalid.");
    /**
     * Error message if the date is invalid
     */
    protected static final String INVALID_DATE_ERROR = createError("Given date is invalid.");
    /**
     * Error message if the priority is invalid
     */
    protected static final String INVALID_PRIORITY_ERROR = createError("Given priority is invalid.");
    /**
     * Error message if the ID is invalid
     */
    protected static final String INVALID_ID_ERROR = createError("Given ID is invalid.");
    /**
     * Error message if the tad is invalid
     */
    protected static final String INVALID_TAG_ERROR = createError("Given tag is invalid.");
    /**
     * Error message if the list name is invalid
     */
    protected static final String INVALID_LIST_ERROR = createError("Given list name is invalid.");
    /**
     * Error message if arguments are invalid
     */
    protected static final String INVALID_ARGUMENTS_ERROR = createError("Given arguments are invalid.");
    /**
     * Error message if task is already deleted
     */
    protected static final String SHOW_DELETED_ERROR = createError("Given task is deleted.");
    private static final String EXPECTED_INNER_ARGUMENTS_ERROR_FORMAT 
            = createError("Expected %d arguments but got %d%n");
    private static final String ERROR_PREFIX = "ERROR: ";
    /**
     * The procrastinot platform.
     */
    protected final Procrastinot procrastinot;
    
    /**
     * Creates a new procrastinot platform command.
     * 
     * @param commandName             The name of the command.
     * @param commandHandler          The command handler.
     * @param procrastinot      The procrastinot platform.
     */
    protected ProcrastinotCommand(String commandName, CommandHandler commandHandler,
                                  Procrastinot procrastinot) {
        super(commandName, commandHandler);
        this.procrastinot = Objects.requireNonNull(procrastinot);
    }

    @Override
    public final void execute(String[] commandArguments) {
        executeProcrastinotCommand(commandArguments);
    }

    /**
     * Compares the given number of arguments to the expected number of arguments and prints an error message if they do not match.
     *
     * @param argsLength the number of arguments to compare
     * @param expectedLength the expected number of arguments
     * @return true if the number of arguments matches the expected number, false otherwise
     */
    protected boolean compareArgsLength(int argsLength, int expectedLength) {
        if (argsLength != expectedLength) {
            System.err.printf(EXPECTED_INNER_ARGUMENTS_ERROR_FORMAT, expectedLength, argsLength);
            return false;
        }
        return true;
    }

    /**
     * Executes the command on the car sharing platform.
     * 
     * @param commandArguments The command arguments.
     */
    protected abstract void executeProcrastinotCommand(String[] commandArguments);

    /**
     * Creates an error message.
     * 
     * @param message The message.
     * @return The message as error message.
     */
    protected static String createError(String message) {
        return ERROR_PREFIX + message;
    }
}
