package edu.kit.kastel.ui.commands;

import edu.kit.kastel.exception.TaskNotFoundException;
import edu.kit.kastel.model.Procrastinot;
import edu.kit.kastel.model.Task;
import edu.kit.kastel.ui.ProcrastinotCommand;
import edu.kit.kastel.ui.CommandHandler;

/**
 * Command to toggle the complete statue of a task
 * (including all direct and indirect subtasks).
 *
 * @author uyzlh
 * @version 1.0
 */
public class ToggleCommand extends ProcrastinotCommand {

    private static final int ID_INDEX = 0;
    private static final String COMMAND_NAME = "toggle";
    private static final String TASK_SUCCESS_FORMAT = "toggled %s and %d subtasks%n";
    private static final int EXPECTED_ARGUMENTS_LENGTH = 1;

    /**
     * Instantiates a new delete task command.
     *
     * @param commandHandler the command handler
     * @param procrastinot   the car procrastinot platform
     */
    public ToggleCommand(CommandHandler commandHandler, Procrastinot procrastinot) {
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

        int id = Integer.parseInt(args[ID_INDEX]);
        Task task;
        try {
            task = procrastinot.toggleTask(id);
        } catch (TaskNotFoundException e) {
            System.err.println(createError(e.getMessage()));
            return;
        }
        
        String name = task.getName();
        int numberOfSubtasks = task.getNumberOfSubtasks();
        System.out.printf(TASK_SUCCESS_FORMAT, name, numberOfSubtasks);
    }
}
