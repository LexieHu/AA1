package edu.kit.kastel.ui.commands;

import edu.kit.kastel.exception.IllegalRestoreException;
import edu.kit.kastel.exception.TaskNotFoundException;
import edu.kit.kastel.model.Procrastinot;
import edu.kit.kastel.model.Task;
import edu.kit.kastel.ui.ProcrastinotCommand;
import edu.kit.kastel.ui.CommandHandler;

/**
 * Command to restores a deleted task
 * (including all direct and indirect subtasks).
 *
 * @author uyzlh
 * @version 1.0
 */
public class RestoreCommand extends ProcrastinotCommand {

    private static final int ID_INDEX = 0;
    private static final String COMMAND_NAME = "restore";
    private static final String TASK_SUCCESS_FORMAT = "restored %s and %d subtasks%n";
    private static final int EXPECTED_ARGUMENTS_LENGTH = 1;

    /**
     * Instantiates a new delete task command.
     *
     * @param commandHandler the command handler
     * @param procrastinot   the car procrastinot platform
     */
    public RestoreCommand(CommandHandler commandHandler, Procrastinot procrastinot) {
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
            task = procrastinot.getTask(id);
        } catch (TaskNotFoundException e) {
            System.err.println(createError(e.getMessage()));
            return;
        }

        int numberOfSubtasks = task.getNumberOfSubtasks();
        try {
            task = procrastinot.getTask(id);
            task.restore(true);
        } catch (TaskNotFoundException | IllegalRestoreException e) {
            System.err.println(createError(e.getMessage()));
            return;
        }
        
        String name = task.getName();
        System.out.printf(TASK_SUCCESS_FORMAT, name, numberOfSubtasks);
    }
}
