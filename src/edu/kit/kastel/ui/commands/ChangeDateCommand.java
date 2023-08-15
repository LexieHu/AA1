package edu.kit.kastel.ui.commands;

import edu.kit.kastel.exception.TaskNotFoundException;
import edu.kit.kastel.model.Procrastinot;
import edu.kit.kastel.model.Task;
import edu.kit.kastel.ui.ProcrastinotCommand;
import edu.kit.kastel.ui.CommandHandler;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Command to change the date of a task.
 *
 * @author uyzlh
 * @version 1.0
 */
public class ChangeDateCommand extends ProcrastinotCommand {

    private static final int ID_INDEX = 0;
    private static final int DATE_INDEX = 1;
    private static final String COMMAND_NAME = "change-date";
    private static final String TASK_SUCCESS_FORMAT = "changed %s to %s%n";
    private static final int EXPECTED_ARGUMENTS_LENGTH = 2;

    /**
     * Instantiates a new change date of a task command.
     *
     * @param commandHandler the command handler
     * @param procrastinot   the car procrastinot platform
     */
    public ChangeDateCommand(CommandHandler commandHandler, Procrastinot procrastinot) {
        super(COMMAND_NAME, commandHandler, procrastinot);
    }

    @Override
    protected void executeProcrastinotCommand(String[] args) {

        int argsLength = args.length;
        if (!compareArgsLength(argsLength, EXPECTED_ARGUMENTS_LENGTH)) {
            return;
        }
        if (!args[ID_INDEX].matches(ID_REGEX)) {
            System.err.println(INVALID_ID_ERROR);
            return;
        }
        if (!args[DATE_INDEX].matches(DATE_REGEX)) {
            System.err.println(INVALID_DATE_ERROR);
            return;
        }

        LocalDate localDate;
        try {
            localDate = LocalDate.parse(args[DATE_INDEX], java.time.format.DateTimeFormatter.ofPattern(DATE_PATTERN));
        } catch (DateTimeParseException e) {
            System.err.println(INVALID_DATE_ERROR);
            return;
        }

        int id = Integer.parseInt(args[ID_INDEX]);
        Task task;
        try {
            task = procrastinot.getTask(id);
        } catch (TaskNotFoundException e) {
            System.err.println(createError(e.getMessage()));
            return;
        }

        String name = task.getName();
        task.setDate(localDate);
        System.out.printf(TASK_SUCCESS_FORMAT, name, localDate);
    }

}
