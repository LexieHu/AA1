package edu.kit.kastel.ui.commands;

import edu.kit.kastel.exception.ListNotFoundException;
import edu.kit.kastel.model.Procrastinot;
import edu.kit.kastel.ui.ProcrastinotCommand;
import edu.kit.kastel.ui.CommandHandler;

/**
 * Command to show all tasks (including all direct and indirect subtasks)
 * that are part of a given list.
 *
 * @author uyzlh
 * @version 1.0
 */
public class ListCommand extends ProcrastinotCommand {

    private static final int LIST_NAME_INDEX = 0;
    private static final String COMMAND_NAME = "list";
    private static final int EXPECTED_ARGUMENTS_LENGTH = 1;

    /**
     * Instantiates a new delete task command.
     *
     * @param commandHandler the command handler
     * @param procrastinot   the car procrastinot platform
     */
    public ListCommand(CommandHandler commandHandler, Procrastinot procrastinot) {
        super(COMMAND_NAME, commandHandler, procrastinot);
    }

    @Override
    protected void executeProcrastinotCommand(String[] args) {

        int argsLength = args.length;
        if (!compareArgsLength(argsLength, EXPECTED_ARGUMENTS_LENGTH)) {
            return;
        }
        if (!args[LIST_NAME_INDEX].matches(LIST_NAME_REGEX)) {
            System.err.println(INVALID_LIST_ERROR);
            return;
        }

        String list = args[LIST_NAME_INDEX];
        try {
            if(!procrastinot.printList(list)) {
                System.out.println(NO_OUTPUT);
            }
        } catch (ListNotFoundException e) {
            System.err.println(createError(e.getMessage()));
        }
    }
}
