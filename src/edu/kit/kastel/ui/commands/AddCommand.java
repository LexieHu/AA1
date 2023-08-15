package edu.kit.kastel.ui.commands;

import edu.kit.kastel.model.Priority;
import edu.kit.kastel.model.Procrastinot;
import edu.kit.kastel.model.Task;
import edu.kit.kastel.ui.ProcrastinotCommand;
import edu.kit.kastel.ui.CommandHandler;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Command to add a task to the default task list in Procrastinot.
 *
 * @author uyzlh
 * @version 1.0
 */
public class AddCommand extends ProcrastinotCommand {

    private static final int NAME_INDEX = 0;
    private static final int PRIORITY_INDEX = 1;
    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 3;
    private static final String COMMAND_NAME = "add";
    private static final String TASK_SUCCESS_FORMAT = "added %d: %s%n";

    /**
     * Instantiates a new add task command.
     *
     * @param commandHandler the command handler
     * @param procrastinot   the car procrastinot platform
     */
    public AddCommand(CommandHandler commandHandler, Procrastinot procrastinot) {
        super(COMMAND_NAME, commandHandler, procrastinot);
    }

    @Override
    protected void executeProcrastinotCommand(String[] args) {

        if ((args.length < MIN_LENGTH) || (args.length > MAX_LENGTH)) {
            System.err.println(INVALID_ARGUMENTS_ERROR);
            return;
        }
        if (!args[NAME_INDEX].matches(NAME_REGEX)) {
            System.err.println(INVALID_TASK_NAME_ERROR);
            return;
        }

        String name = args[NAME_INDEX];
        int id = procrastinot.getDefaultTasks().size() + 1;
        Task task = new Task(false, id, name, Priority.NONE, null);
        boolean hasPriority = false;
        for (int i = PRIORITY_INDEX; i < args.length; i++) {
            if (args[i].matches(PRIORITY_REGEX) && !hasPriority) {
                Priority priority = Priority.fromString(args[i]);
                task.setPriority(priority);
                hasPriority = true;
                continue;
            } else if (args[i].matches(DATE_REGEX)) {
                LocalDate localDate;
                try {
                    localDate = LocalDate.parse(args[i]);
                } catch (DateTimeParseException e) {
                    System.err.println(INVALID_DATE_ERROR);
                    return;
                }
                task.setDate(localDate);
            } else {
                System.err.println(INVALID_ARGUMENTS_ERROR);
                return;
            }
        }

        if ((args.length > PRIORITY_INDEX) && (args[PRIORITY_INDEX].matches(DATE_REGEX)) && (args.length == MAX_LENGTH)) {
            System.err.println(INVALID_ARGUMENTS_ERROR);
            return;
        }

        procrastinot.getDefaultTasks().add(task);
        System.out.printf(TASK_SUCCESS_FORMAT, id, name);
    }
}
