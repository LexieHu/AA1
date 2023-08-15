package edu.kit.kastel.ui.commands;

import edu.kit.kastel.exception.TaskNotFoundException;
import edu.kit.kastel.model.Priority;
import edu.kit.kastel.model.Procrastinot;
import edu.kit.kastel.model.Task;
import edu.kit.kastel.ui.ProcrastinotCommand;
import edu.kit.kastel.ui.CommandHandler;

/**
 * Command to change the Priority of a task.
 *
 * @author uyzlh
 * @version 1.0
 */
public class ChangePriorityCommand extends ProcrastinotCommand {

    private static final int ID_INDEX = 0;
    private static final int PRIORITY_INDEX = 1;
    private static final String COMMAND_NAME = "change-priority";
    private static final String TASK_SUCCESS_FORMAT = "changed %s to %s%n";
    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 2;

    /**
     * Instantiates a new priority from the task command.
     *
     * @param commandHandler the command handler
     * @param procrastinot   the car procrastinot platform
     */
    public ChangePriorityCommand(CommandHandler commandHandler, Procrastinot procrastinot) {
        super(COMMAND_NAME, commandHandler, procrastinot);
    }

    @Override
    protected void executeProcrastinotCommand(String[] args) {

        int argsLength = args.length;
        if ((argsLength < MIN_LENGTH) || (argsLength > MAX_LENGTH)) {
            System.err.println(INVALID_ARGUMENTS_ERROR);
            return;
        }
        if (!args[ID_INDEX].matches(ID_REGEX)) {
            System.err.println(INVALID_ID_ERROR);
            return;
        }
        int id = Integer.parseInt(args[ID_INDEX]);
        if (argsLength != MIN_LENGTH && !args[PRIORITY_INDEX].matches(PRIORITY_REGEX)) {
            System.err.println(INVALID_PRIORITY_ERROR);
            return;
        }

        Task task;
        try {
            task = procrastinot.getTask(id);
        } catch (TaskNotFoundException e) {
            System.err.println(createError(e.getMessage()));
            return;
        }

        Priority priority;
        String name = task.getName();
        if (argsLength == MIN_LENGTH) {
            priority = Priority.NONE;
            task.setPriority(priority);
        } else {
            priority = Priority.valueOf(args[PRIORITY_INDEX]);
            task.setPriority(priority);
        }
        System.out.printf(TASK_SUCCESS_FORMAT, name, priority);
    }
}
