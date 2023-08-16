package edu.kit.kastel.ui.commands;

import edu.kit.kastel.exception.IllegalAddListException;
import edu.kit.kastel.model.Procrastinot;
import edu.kit.kastel.model.TaskList;
import edu.kit.kastel.ui.CommandHandler;
import edu.kit.kastel.ui.ProcrastinotCommand;

/**
 * Command to add a list to Procrastinot.
 *
 * @author uyzlh
 * @version 1.0
 */
public class AddListCommand extends ProcrastinotCommand {

    private static final int LIST_NAME_INDEX = 0;
    private static final String COMMAND_NAME = "add-list";
    private static final String TASK_SUCCESS_FORMAT = "added %s%n";
    private static final int EXPECTED_ARGUMENTS_LENGTH = 1;

    /**
     * Instantiates a new add list command.
     *
     * @param commandHandler        the command handler
     * @param procrastinot    the procrastinot platform
     */
    public AddListCommand(CommandHandler commandHandler, Procrastinot procrastinot) {
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

        TaskList list = new TaskList(args[LIST_NAME_INDEX]);
        try {
            procrastinot.addList(list);
        } catch (IllegalAddListException e) {
            System.err.println(createError(e.getMessage()));
            return;
        }

        String listName = args[LIST_NAME_INDEX];
        System.out.printf(TASK_SUCCESS_FORMAT, listName);
    }
}
