package edu.kit.kastel.ui.commands;

import edu.kit.kastel.model.Procrastinot;
import edu.kit.kastel.ui.ProcrastinotCommand;
import edu.kit.kastel.ui.CommandHandler;

/**
 * Command to find all tasks (including all direct and indirect subtasks)
 * where the name match a desired character string.
 *
 * @author uyzlh
 * @version 1.0
 */
public class FindCommand extends ProcrastinotCommand {

    private static final String COMMAND_NAME = "find";
    private static final int NAME_INDEX = 0;
    private static final int EXPECTED_ARGUMENTS_LENGTH = 1;

    /**
     * Instantiates a new delete task command.
     *
     * @param commandHandler the command handler
     * @param procrastinot   the car procrastinot platform
     */
    public FindCommand(CommandHandler commandHandler, Procrastinot procrastinot) {
        super(COMMAND_NAME, commandHandler, procrastinot);
    }

    @Override
    protected void executeProcrastinotCommand(String[] args) {

        int argsLength = args.length;
        if (!compareArgsLength(argsLength, EXPECTED_ARGUMENTS_LENGTH)) {
            return;
        }
        if (!args[NAME_INDEX].matches(NAME_REGEX)) {
            System.err.println(INVALID_TASK_NAME_ERROR);
            return;
        }

        String name = args[NAME_INDEX];
        try {
            procrastinot.findTasksWithName(name);
        } catch (Exception e) {
            System.err.println(createError(e.getMessage()));
        }
    }
}

